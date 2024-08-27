package com.studystream.app.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object RoleTable : IntIdTable("role") {
    val title = varchar("title", 255)
}