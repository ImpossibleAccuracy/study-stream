package com.studystream.domain.service

import com.studystream.domain.model.Document
import com.studystream.domain.model.MultipartFile
import com.studystream.domain.model.StorageCatalog
import java.io.File

interface FileStorageService {
    suspend fun getFile(document: Document): Result<File>

    /**
     * Saves the file in the specified catalog
     *
     * @param multipart File to save
     * @param catalog File destination
     * @return File stored in specified catalog
     */
    suspend fun store(multipart: MultipartFile, catalog: StorageCatalog): Result<Document>

    /**
     * Moves file to specified catalog
     *
     * @param document File to move
     * @param catalog File destination catalog
     * @return File stored in destination catalog
     */
    suspend fun move(document: Document, catalog: StorageCatalog): Result<Document>

    /**
     * Deletes document and linked file
     *
     * @param document File to delete
     * @return true when the file successfully deleted, false if fails.
     */
    suspend fun delete(document: Document)

    /**
     * Clear specified catalog
     *
     * @param catalog Catalog to clear
     */
    suspend fun clearCatalog(catalog: StorageCatalog)
}
