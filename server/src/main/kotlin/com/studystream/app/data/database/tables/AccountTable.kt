package com.studystream.app.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object AccountTable : IntIdTable("account") {
    val username = varchar("username", 255)
    val password = varchar("password", 255)
}