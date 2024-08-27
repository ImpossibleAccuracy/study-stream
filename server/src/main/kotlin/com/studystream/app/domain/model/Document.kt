package com.studystream.app.domain.model

import com.studystream.app.data.database.base.BaseModel
import com.studystream.app.data.database.dao.DocumentTypeDao
import com.studystream.app.data.database.tables.DocumentTable
import com.studystream.app.data.database.tables.DocumentTypeTable
import org.jetbrains.exposed.dao.id.EntityID

class Document(id: EntityID<Id>) : BaseModel(id, DocumentTable) {
    var title: String by DocumentTable.title
    var hash: String by DocumentTable.hash
    var path: String by DocumentTable.path
    var type: Type by DocumentTypeDao referencedOn DocumentTable.type

    class Type(id: EntityID<Id>) : BaseModel(id, DocumentTypeTable) {
        var title: String by DocumentTypeTable.title
        var mimeType: String by DocumentTypeTable.mimeType
    }
}
