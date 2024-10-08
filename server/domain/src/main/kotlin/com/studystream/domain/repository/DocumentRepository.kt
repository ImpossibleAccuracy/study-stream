package com.studystream.domain.repository

import com.studystream.domain.model.Document

interface DocumentRepository {
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

    suspend fun findByHash(hash: String): Document?

    suspend fun findTypeByMimeType(mimeType: String): Result<Document.Type>

    suspend fun update(document: Document): Result<Document>

    suspend fun delete(id: Int): Result<Unit>
}