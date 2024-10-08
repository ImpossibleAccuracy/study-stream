package com.studystream.data.repository

import com.studystream.data.utils.ioCatchingCall
import com.studystream.data.utils.replace
import com.studystream.data.utils.substring
import com.studystream.domain.exception.ResourceNotFoundException
import com.studystream.domain.model.Document
import com.studystream.domain.model.MultipartFile
import com.studystream.domain.model.StorageCatalog
import com.studystream.domain.properties.feature.FileStorageProperties
import com.studystream.domain.repository.DocumentRepository
import com.studystream.domain.repository.FileStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URLConnection
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.time.LocalDateTime
import kotlin.io.path.*

@OptIn(ExperimentalPathApi::class, ExperimentalStdlibApi::class)
class FileStorageRepositoryImpl(
    private val properties: FileStorageProperties,
    private val documentRepository: DocumentRepository,
) : FileStorageRepository {
    companion object {
        private const val MAX_ORIGINAL_NAME_LENGTH = 15
        private const val DEFAULT_MIME_TYPE = "plain/text"

        private const val FILE_NAME_PATTERN = "{FILE_NAME}"
        private const val FILE_HASH_PATTERN = "{FILE_HASH}"
        private const val TIMESTAMP_PATTERN = "{TIMESTAMP}"
        private const val EXTENSION_PATTERN = "{EXTENSION}"
    }

    private val logger = LoggerFactory.getLogger(FileStorageRepository::class.simpleName!!)

    init {
        StorageCatalog.entries.forEach {
            val catalogPath = it.path(properties)
            catalogPath.createDirectories()
        }
    }

    override suspend fun getFile(document: Document): Result<File> = ioCatchingCall {
        val path = Path.of(document.path)

        if (!path.exists()) {
            throw ResourceNotFoundException("File deleted")
        }

        path.toFile()
    }

    override suspend fun store(multipart: MultipartFile, catalog: StorageCatalog): Result<Document> = ioCatchingCall {
        val hash = computeFileHash(multipart.bytes)

        val storedDocument = documentRepository.findByHash(hash)
        if (storedDocument != null) return@ioCatchingCall storedDocument

        val fileName = computeFileName(multipart.name, multipart.bytes)

        val catalogPath = catalog.path(properties)
        val destination = catalogPath.resolve(fileName)

        val file = destination
            .createFile()
            .toFile()

        file.writeBytes(multipart.bytes)

        logger.debug(
            "File \"${multipart.name}\" stored into $catalog catalog as \"${file.absoluteFile}\""
        )

        return@ioCatchingCall documentRepository
            .save(
                title = properties.maxFilenameLength?.let { substring(file.name, it) }
                    ?: file.name,
                hash = hash,
                path = file.absolutePath,
                type = getDocumentType(multipart),
            )
            .getOrThrow()
    }

    private fun computeFileHash(bytes: ByteArray): String {
        val md = MessageDigest.getInstance("MD5")

        val digest = md.digest(bytes)

        return digest.toHexString()
    }

    private fun computeFileName(originalFileName: String, content: ByteArray): String = buildString {
        append(properties.fileNamePattern)

        replace(FILE_NAME_PATTERN) {
            val string = originalFileName.substringBeforeLast(".")

            substring(string, MAX_ORIGINAL_NAME_LENGTH)
        }

        replace(FILE_HASH_PATTERN) {
            computeFileHash(content)
        }

        replace(TIMESTAMP_PATTERN) {
            LocalDateTime.now().toString()
        }

        replace(EXTENSION_PATTERN) {
            originalFileName.substringAfterLast(".")
        }

        replace(":") { "-" }
    }

    private suspend fun getDocumentType(file: MultipartFile): Document.Type {
        val mimeType = file.contentType
            ?: URLConnection.guessContentTypeFromName(file.name)
            ?: DEFAULT_MIME_TYPE

        return documentRepository.findTypeByMimeType(mimeType).getOrElse {
            documentRepository
                .saveType(
                    title = "From MIME type $mimeType",
                    mimeType = mimeType,
                )
                .getOrThrow()
        }
    }

    override suspend fun move(document: Document, catalog: StorageCatalog): Result<Document> = ioCatchingCall {
        val catalogPath = catalog.path(properties).absolute()
        val file = Path.of(document.path)

        if (file.startsWith(catalogPath)) {
            return@ioCatchingCall document
        }

        val resultPath = Files.move(
            file,
            catalogPath.resolve(file.name),
        )

        val resultFile = resultPath.toFile()

        logger.debug(
            "File \"${file.name}\" moved into $catalog catalog as \"${resultFile.absoluteFile}\""
        )

        document.path = resultPath.absolutePathString()

        return@ioCatchingCall document
    }

    override suspend fun delete(document: Document): Unit = withContext(Dispatchers.IO) {
        val file = Path.of(document.path)
        file.deleteIfExists()

        documentRepository.delete(document.idValue)
            .onSuccess {
                logger.debug("File \"${file.name}\" deleted")
            }
    }

    override suspend fun clearCatalog(catalog: StorageCatalog) = withContext(Dispatchers.IO) {
        val catalogPath = catalog.path(properties)

        catalogPath.deleteRecursively()
    }
}
