package com.studystream.data.database.tables

import com.studystream.data.database.base.BaseTable

object DocumentTypeTable : BaseTable("document_type") {
    val title = varchar("title", 255)
    val mimeType = varchar("mime_type", 255)
}