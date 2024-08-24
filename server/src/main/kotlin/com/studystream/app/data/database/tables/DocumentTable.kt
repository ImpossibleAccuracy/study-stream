package com.studystream.app.data.database.tables

import com.studystream.app.data.database.base.BaseTable

object DocumentTable : BaseTable("document") {
    val title = varchar("title", 255)
    val hash = varchar("hash", 255)
    val path = varchar("path", 2048)
    val type = reference("type_id", DocumentTypeTable)
}