package com.studystream.data.service

import com.studystream.data.database.dao.DocumentDao
import com.studystream.data.database.dao.DocumentTypeDao
import com.studystream.data.database.tables.DocumentTable
import com.studystream.data.database.tables.DocumentTypeTable
import com.studystream.data.database.utils.runCatchingTransaction
import com.studystream.data.mapper.toDomain
import com.studystream.domain.model.Document
import com.studystream.domain.service.DocumentService

class DocumentServiceImpl : DocumentService {
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
}