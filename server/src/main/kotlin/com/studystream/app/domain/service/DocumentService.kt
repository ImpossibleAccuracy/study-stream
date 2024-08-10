package com.studystream.app.domain.service

import com.studystream.app.domain.model.Document

interface DocumentService {
    suspend fun findById(id: Int): Result<Document>

    suspend fun findByHash(hash: String): Result<Document>

    suspend fun findTypeByMimeType(mimeType: String): Result<Document.Type>

    suspend fun save(
        title: String,
        hash: String,
        path: String,
        type: Document.Type,
    ): Result<Document>

    suspend fun saveType(
        title: String,
        mimeType: String,
    ): Result<Document.Type>

    suspend fun update(id: Int, document: Document): Result<Document>

    suspend fun delete(id: Int): Result<Unit>
}