package com.studystream.data.datasource.base

import com.studystream.domain.model.Document

interface DocumentTypeDataSource : CrudDataSource<Document.Type> {
    suspend fun findByMimeType(mimeType: String): Document.Type?
}