package com.studystream.app.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object DocumentTable : IntIdTable("document") {
    val title = varchar("title", 255)
    val hash = varchar("hash", 255)
    val path = varchar("path", 2048)
    val type = reference("type_id", DocumentTypeTable)
}