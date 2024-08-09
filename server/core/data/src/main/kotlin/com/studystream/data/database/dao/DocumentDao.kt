package com.studystream.data.database.dao

import com.studystream.data.database.tables.DocumentTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DocumentDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DocumentDao>(DocumentTable)

    var title by DocumentTable.title
    var hash by DocumentTable.hash
    var path by DocumentTable.path
    var type by DocumentTypeDao referencedOn DocumentTable.type
}