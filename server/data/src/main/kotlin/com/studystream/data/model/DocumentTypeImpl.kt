package com.studystream.data.model

import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.tables.DocumentTypeTable
import com.studystream.domain.model.Document
import com.studystream.domain.model.Id
import org.jetbrains.exposed.dao.id.EntityID

class DocumentTypeImpl(id: EntityID<Id>) : Document.Type, ModelImpl(id, DocumentTypeTable) {
    override var title: String by DocumentTypeTable.title
    override var mimeType: String by DocumentTypeTable.mimeType
}