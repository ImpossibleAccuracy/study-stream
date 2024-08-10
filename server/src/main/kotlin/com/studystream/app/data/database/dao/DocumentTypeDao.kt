package com.studystream.app.data.database.dao

import com.studystream.app.data.database.tables.DocumentTypeTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DocumentTypeDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DocumentTypeDao>(DocumentTypeTable)

    var title by DocumentTypeTable.title
    var mimeType by DocumentTypeTable.mimeType
}