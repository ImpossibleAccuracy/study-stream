package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.DocumentTypeDao
import com.studystream.data.database.tables.DocumentTypeTable
import com.studystream.data.database.utils.findSingle
import com.studystream.data.datasource.base.DocumentTypeDataSource
import com.studystream.data.model.DocumentTypeImpl
import com.studystream.domain.model.Document
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DocumentTypeDataSourceImpl : DocumentTypeDataSource,
    CrudDataSourceImpl<Document.Type, DocumentTypeImpl>(DocumentTypeDao) {
    override suspend fun findByMimeType(mimeType: String): Document.Type? =
        DocumentTypeDao.findSingle(DocumentTypeTable.mimeType eq mimeType)
}