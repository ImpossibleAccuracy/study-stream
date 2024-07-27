package com.solovocal.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("account") {
    val name = varchar("username", 50).index()
    val avatarId = integer("avatar_id")
}