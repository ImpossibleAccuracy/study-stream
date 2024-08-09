package com.studystream.domain.service

import com.studystream.domain.model.Document

interface DocumentService {
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
}