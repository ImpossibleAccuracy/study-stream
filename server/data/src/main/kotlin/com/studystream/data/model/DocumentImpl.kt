package com.studystream.data.model

import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.dao.DocumentTypeDao
import com.studystream.data.database.extensions.mapper.withMapper
import com.studystream.data.database.tables.DocumentTable
import com.studystream.domain.model.Document
import com.studystream.domain.model.Id
import org.jetbrains.exposed.dao.id.EntityID

class DocumentImpl(id: EntityID<Id>) : Document, ModelImpl(id, DocumentTable) {
    override var title: String by DocumentTable.title
    override var hash: String by DocumentTable.hash
    override var path: String by DocumentTable.path

    override var type: Document.Type by DocumentTypeDao.referencedOn(DocumentTable.type).withMapper()
}