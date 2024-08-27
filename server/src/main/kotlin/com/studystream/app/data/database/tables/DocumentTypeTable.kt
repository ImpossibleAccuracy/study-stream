package com.studystream.app.data.database.tables

import com.studystream.app.data.database.base.BaseTable

object DocumentTypeTable : BaseTable("document_type") {
    val title = varchar("title", 255)
    val mimeType = varchar("mime_type", 255)
}