package com.studystream.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object PrivilegeTable : IntIdTable("privilege") {
    val title = varchar("title", 255)
}