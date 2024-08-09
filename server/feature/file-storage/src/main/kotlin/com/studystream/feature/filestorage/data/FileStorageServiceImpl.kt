package com.studystream.feature.filestorage.data

import com.studystream.domain.model.Document
import com.studystream.domain.properties.feature.FileStorageProperties
import com.studystream.domain.service.DocumentService
import com.studystream.feature.filestorage.data.utils.replace
import com.studystream.feature.filestorage.data.utils.substring
import com.studystream.feature.filestorage.domain.FileStorageService
import com.studystream.feature.filestorage.domain.model.MultipartFile
import com.studystream.feature.filestorage.domain.model.StorageCatalog
import io.ktor.util.logging.*
import java.io.File
import java.net.URLConnection
import java.nio.file.Files
import java.security.MessageDigest
import java.time.LocalDateTime
import kotlin.io.path.*

@OptIn(ExperimentalPathApi::class, ExperimentalStdlibApi::class)
class FileStorageServiceImpl(
    private val properties: FileStorageProperties,
    private val documentService: DocumentService,
    private val logger: Logger,
) : FileStorageService {
    companion object {
        private const val MAX_ORIGINAL_NAME_LENGTH = 15
        private const val DEFAULT_MIME_TYPE = "plain/text"

        private const val FILE_NAME_PATTERN = "{FILE_NAME}"
        private const val FILE_HASH_PATTERN = "{FILE_HASH}"
        private const val TIMESTAMP_PATTERN = "{TIMESTAMP}"
        private const val EXTENSION_PATTERN = "{EXTENSION}"
    }

    init {
        StorageCatalog.entries.forEach {
            val catalogPath = it.path(properties)
            catalogPath.createDirectories()
        }
    }

    override suspend fun findDocumentOrCreate(file: File): Document {
        val hash = getFileHash(file.readBytes())

        return documentService.findByHash(hash)
            .getOrElse {
                val fileInStorage = move(file, StorageCatalog.Regular)

                documentService
                    .save(
                        title = properties.maxFilenameLength?.let { substring(fileInStorage.name, it) }
                            ?: fileInStorage.name,
                        hash = getFileHash(fileInStorage.readBytes()),
                        path = fileInStorage.absolutePath,
                        type = getDocumentType(fileInStorage),
                    )
                    .getOrThrow()
            }
    }

    private suspend fun getDocumentType(file: File): Document.Type =
        URLConnection.guessContentTypeFromName(file.name).let { contentMimeType ->
            val mimeType = contentMimeType ?: DEFAULT_MIME_TYPE

            documentService.findTypeByMimeType(mimeType)
                .getOrElse {
                    documentService
                        .saveType(
                            title = "From MIME type $mimeType",
                            mimeType = mimeType,
                        )
                        .getOrThrow()
                }
        }

    private fun getFileHash(bytes: ByteArray): String {
        val md = MessageDigest.getInstance("MD5")

        val digest = md.digest(bytes)

        return digest.toHexString()
    }

    override suspend fun store(file: MultipartFile, catalog: StorageCatalog): File {
        val fileName = computeFileName(file.name, file.bytes)

        val catalogPath = catalog.path(properties)
        val destination = catalogPath.resolve(fileName)

        destination
            .createFile()
            .writeBytes(file.bytes)

        val resultFile = destination.toFile()

        logger.info(
            "File \"${file.name}\" stored into $catalog catalog as ${resultFile.absoluteFile}"
        )

        return resultFile
    }

    private fun computeFileName(originalFileName: String, content: ByteArray): String = buildString {
        append(properties.fileNamePattern)

        replace(FILE_NAME_PATTERN) {
            val string = originalFileName.substringBeforeLast(".")

            substring(string, MAX_ORIGINAL_NAME_LENGTH)
        }

        replace(FILE_HASH_PATTERN) {
            getFileHash(content)
        }

        replace(TIMESTAMP_PATTERN) {
            LocalDateTime.now().toString()
        }

        replace(EXTENSION_PATTERN) {
            originalFileName.substringAfterLast(".")
        }

        replace(":") { "-" }
    }

    override suspend fun move(file: File, catalog: StorageCatalog): File {
        val catalogPath = catalog.path(properties)
        val filePath = file.toPath()

        if (filePath.contains(catalogPath)) {
            return file
        }

        val resultPath = Files.move(
            filePath,
            catalogPath.resolve(file.name),
        )

        val resultFile = resultPath.toFile()

        logger.info(
            "File ${file.name} moved into $catalog catalog as ${resultFile.absoluteFile}"
        )

        return resultFile
    }

    override suspend fun delete(file: File, catalog: StorageCatalog): Boolean {
        val catalogPath = catalog.path(properties).toAbsolutePath()
        val filePath = file.toPath().toAbsolutePath()

        if (!filePath.startsWith(catalogPath)) {
            return false
        }

        return file.delete().also {
            if (it) {
                logger.info("File ${file.name} deleted from $catalog catalog")
            }
        }
    }

    override suspend fun clearCatalog(catalog: StorageCatalog) {
        val catalogPath = catalog.path(properties)

        catalogPath.deleteRecursively()
    }
}
