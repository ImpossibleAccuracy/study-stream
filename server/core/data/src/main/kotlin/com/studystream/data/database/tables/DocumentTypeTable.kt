package com.studystream.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object DocumentTypeTable : IntIdTable("document_type") {
    val title = varchar("title", 255)
    val mimeType = varchar("mime_type", 255)
}