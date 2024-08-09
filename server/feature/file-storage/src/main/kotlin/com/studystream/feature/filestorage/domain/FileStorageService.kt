package com.studystream.feature.filestorage.domain

import com.studystream.domain.model.Document
import com.studystream.feature.filestorage.domain.model.MultipartFile
import com.studystream.feature.filestorage.domain.model.StorageCatalog
import java.io.File

interface FileStorageService {

    /**
     * Searches for a file in the database by hash.
     *
     * If the file is not found, saves file to the [StorageCatalog.Regular] catalog and creates record in database.
     *
     * @param file File to save
     * @return File stored in regular catalog
     */
    suspend fun findDocumentOrCreate(file: File): Document

    /**
     * Saves the file in the specified catalog
     *
     * @param file File to save
     * @param catalog File destination
     * @return File stored in specified catalog
     */
    suspend fun store(file: MultipartFile, catalog: StorageCatalog): File

    /**
     * Moves file to specified catalog
     *
     * @param file File to move
     * @param catalog File destination catalog
     * @return File stored in destination catalog
     */
    suspend fun move(file: File, catalog: StorageCatalog): File

    /**
     * Deletes file from catalog
     *
     * @param file File to delete
     * @param catalog File storage catalog
     * @return true when the file successfully deleted, false if fails.
     */
    suspend fun delete(file: File, catalog: StorageCatalog): Boolean

    /**
     * Clear specified catalog
     *
     * @param catalog Catalog to clear
     */
    suspend fun clearCatalog(catalog: StorageCatalog)
}
