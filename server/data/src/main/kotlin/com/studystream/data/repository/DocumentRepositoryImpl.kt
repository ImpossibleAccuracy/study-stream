package com.studystream.data.repository

import com.studystream.data.datasource.base.DocumentDataSource
import com.studystream.data.datasource.base.DocumentTypeDataSource
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.model.Document
import com.studystream.domain.repository.DocumentRepository
import com.studystream.domain.utils.require

class DocumentRepositoryImpl(
    private val documentDataSource: DocumentDataSource,
    private val documentTypeDataSource: DocumentTypeDataSource,
) : DocumentRepository {
    override suspend fun save(
        title: String,
        hash: String,
        path: String,
        type: Document.Type,
    ): Result<Document> =
        ioCatchingCall {
            val documentType = documentTypeDataSource.findById(type.idValue).require()

            documentDataSource.save {
                this.title = title
                this.hash = hash
                this.path = path
                this.type = documentType
            }
        }

    override suspend fun saveType(title: String, mimeType: String): Result<Document.Type> =
        ioCatchingCall {
            documentTypeDataSource.save {
                this.title = title
                this.mimeType = mimeType
            }
        }

    override suspend fun findByHash(hash: String): Document? =
        ioCall {
            documentDataSource.findByHash(hash)
        }

    override suspend fun findTypeByMimeType(mimeType: String): Result<Document.Type> =
        ioCatchingCall {
            documentTypeDataSource
                .findByMimeType(mimeType)
                .require()
        }

    override suspend fun update(document: Document): Result<Document> =
        ioCatchingCall {
            documentDataSource.save(document.idValue) {
                this.title = document.title
                this.hash = document.hash
                this.path = document.path
                this.type = document.type
            }
        }

    override suspend fun delete(id: Int): Result<Unit> = ioCatchingCall {
        documentDataSource.deleteById(id)
    }
}