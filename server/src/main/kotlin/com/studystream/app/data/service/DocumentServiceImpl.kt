package com.studystream.app.data.service

import com.studystream.app.data.database.dao.DocumentDao
import com.studystream.app.data.database.dao.DocumentTypeDao
import com.studystream.app.data.database.tables.DocumentTable
import com.studystream.app.data.database.tables.DocumentTypeTable
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.service.DocumentService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class DocumentServiceImpl : DocumentService {
    override suspend fun save(title: String, hash: String, path: String, type: Document.Type): Result<Document> =
        runCatchingTransaction {
            DocumentDao.new {
                this.title = title
                this.hash = hash
                this.path = path
                this.type = DocumentTypeDao.findById(type.id)!!
            }
        }

    override suspend fun saveType(title: String, mimeType: String): Result<Document.Type> =
        runCatchingTransaction {
            DocumentTypeDao.new {
                this.title = title
                this.mimeType = mimeType
            }
        }

    override suspend fun findById(id: Int): Document? =
        runSuspendedTransaction {
            DocumentDao.findById(id)
        }

    override suspend fun findByHash(hash: String): Document =
        runSuspendedTransaction {
            DocumentDao
                .find {
                    DocumentTable.hash eq hash
                }
                .first()
        }

    override suspend fun findTypeByMimeType(mimeType: String): Document.Type? =
        runSuspendedTransaction {
            DocumentTypeDao
                .find {
                    DocumentTypeTable.mimeType eq mimeType
                }
                .firstOrNull()
        }

    override suspend fun update(document: Document): Result<Document> =
        runCatchingTransaction {
            DocumentDao.findByIdAndUpdate(document.id.value) {
                it.title = document.title
                it.hash = document.hash
                it.path = document.path
                it.type = DocumentTypeDao.findById(document.type.id)!!
            }!!
        }

    override suspend fun delete(id: Int): Result<Unit> = runCatchingTransaction {
        DocumentTable.deleteWhere {
            DocumentTable.id eq id
        }
    }
}