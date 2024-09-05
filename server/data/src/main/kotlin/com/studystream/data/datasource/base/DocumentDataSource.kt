package com.studystream.data.datasource.base

import com.studystream.domain.model.Document

interface DocumentDataSource : CrudDataSource<Document> {
    suspend fun findByHash(hash: String): Document?
}