package com.studystream.data.service

import com.studystream.data.database.dao.DocumentDao
import com.studystream.data.database.dao.DocumentTypeDao
import com.studystream.data.database.tables.DocumentTable
import com.studystream.data.database.tables.DocumentTypeTable
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.model.Document
import com.studystream.domain.service.DocumentService
import com.studystream.domain.utils.require

class DocumentServiceImpl(
    private val documentDao: DocumentDao,
    private val documentTypeDao: DocumentTypeDao,
) : DocumentService {
    override suspend fun save(title: String, hash: String, path: String, type: Document.Type): Result<Document> =
        ioCatchingCall {
            documentDao.new {
                this.title = title
                this.hash = hash
                this.path = path
                this.type = documentTypeDao.findById(type.idValue)!!
            }
        }

    override suspend fun saveType(title: String, mimeType: String): Result<Document.Type> =
        ioCatchingCall {
            documentTypeDao.new {
                this.title = title
                this.mimeType = mimeType
            }
        }

    override suspend fun findByHash(hash: String): Document? =
        ioCall {
            documentDao
                .find {
                    DocumentTable.hash eq hash
                }
                .firstOrNull()
        }

    override suspend fun findTypeByMimeType(mimeType: String): Result<Document.Type> =
        ioCatchingCall {
            documentTypeDao
                .find {
                    DocumentTypeTable.mimeType eq mimeType
                }
                .firstOrNull()
                .require()
        }

    override suspend fun update(document: Document): Result<Document> =
        ioCatchingCall {
            documentDao.findByIdAndUpdate(document.idValue) {
                it.title = document.title
                it.hash = document.hash
                it.path = document.path
                it.type = documentTypeDao.findById(document.type.idValue)!!
            }!!
        }

    override suspend fun delete(id: Int): Result<Unit> = ioCatchingCall {
        documentDao.findById(id)?.delete()
    }
}