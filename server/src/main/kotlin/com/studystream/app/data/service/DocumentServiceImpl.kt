package com.studystream.app.data.service

import com.studystream.app.data.database.dao.DocumentDao
import com.studystream.app.data.database.dao.DocumentTypeDao
import com.studystream.app.data.database.tables.DocumentTable
import com.studystream.app.data.database.tables.DocumentTypeTable
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.service.DocumentService
import com.studystream.app.domain.utils.require

class DocumentServiceImpl(
    private val documentDao: DocumentDao,
    private val documentTypeDao: DocumentTypeDao,
) : DocumentService {
    override suspend fun save(title: String, hash: String, path: String, type: Document.Type): Result<Document> =
        runCatchingTransaction {
            documentDao.new {
                this.title = title
                this.hash = hash
                this.path = path
                this.type = documentTypeDao.findById(type.id)!!
            }
        }

    override suspend fun saveType(title: String, mimeType: String): Result<Document.Type> =
        runCatchingTransaction {
            documentTypeDao.new {
                this.title = title
                this.mimeType = mimeType
            }
        }

    override suspend fun findByHash(hash: String): Document? =
        runSuspendedTransaction {
            documentDao
                .find {
                    DocumentTable.hash eq hash
                }
                .firstOrNull()
        }

    override suspend fun findTypeByMimeType(mimeType: String): Result<Document.Type> =
        runCatchingTransaction {
            documentTypeDao
                .find {
                    DocumentTypeTable.mimeType eq mimeType
                }
                .firstOrNull()
                .require()
        }

    override suspend fun update(document: Document): Result<Document> =
        runCatchingTransaction {
            documentDao.findByIdAndUpdate(document.idValue) {
                it.title = document.title
                it.hash = document.hash
                it.path = document.path
                it.type = documentTypeDao.findById(document.type.id)!!
            }!!
        }

    override suspend fun delete(id: Int): Result<Unit> = runCatchingTransaction {
        documentDao.findById(id)?.delete()
    }
}