package com.studystream.data.service

import com.studystream.data.database.dao.DocumentDao
import com.studystream.data.database.dao.DocumentTypeDao
import com.studystream.data.database.tables.DocumentTable
import com.studystream.data.database.tables.DocumentTypeTable
import com.studystream.data.database.utils.runCatchingTransaction
import com.studystream.data.mapper.toDomain
import com.studystream.domain.model.Document
import com.studystream.domain.service.DocumentService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class DocumentServiceImpl : DocumentService {
    override suspend fun findById(id: Int): Result<Document> =
        runCatchingTransaction {
            DocumentDao
                .findById(id)!!
                .toDomain()
        }

    override suspend fun findByHash(hash: String): Result<Document> =
        runCatchingTransaction {
            DocumentDao
                .find {
                    DocumentTable.hash eq hash
                }
                .first()
                .toDomain()
        }

    override suspend fun findTypeByMimeType(mimeType: String): Result<Document.Type> =
        runCatchingTransaction {
            DocumentTypeDao
                .find {
                    DocumentTypeTable.mimeType eq mimeType
                }
                .first()
                .toDomain()
        }

    override suspend fun save(title: String, hash: String, path: String, type: Document.Type): Result<Document> =
        runCatchingTransaction {
            DocumentDao
                .new {
                    this.title = title
                    this.hash = hash
                    this.path = path
                    this.type = DocumentTypeDao.findById(type.id)!!
                }
                .toDomain()
        }

    override suspend fun saveType(title: String, mimeType: String): Result<Document.Type> =
        runCatchingTransaction {
            DocumentTypeDao
                .new {
                    this.title = title
                    this.mimeType = mimeType
                }
                .toDomain()
        }

    override suspend fun update(id: Int, document: Document): Result<Document> =
        runCatchingTransaction {
            DocumentDao
                .findByIdAndUpdate(id) {
                    it.title = document.title
                    it.hash = document.hash
                    it.path = document.path
                    it.type = DocumentTypeDao.findById(document.type.id)!!
                }!!
                .toDomain()
        }

    override suspend fun delete(id: Int): Result<Unit> = runCatchingTransaction {
        DocumentTable.deleteWhere {
            DocumentTable.id eq id
        }
    }
}