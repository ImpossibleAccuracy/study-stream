package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.DocumentDao
import com.studystream.data.database.tables.DocumentTable
import com.studystream.data.database.utils.findSingle
import com.studystream.data.datasource.base.DocumentDataSource
import com.studystream.data.model.DocumentImpl
import com.studystream.domain.model.Document
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DocumentDataSourceImpl : DocumentDataSource, CrudDataSourceImpl<Document, DocumentImpl>(DocumentDao) {
    override suspend fun findByHash(hash: String): Document? =
        DocumentDao.findSingle(DocumentTable.hash eq hash)
}