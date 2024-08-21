package com.studystream.app.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object ProfileTable : IntIdTable("profile") {
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val patronymic = varchar("patronymic", 255).nullable()
    val birthday = date("birthday")
    val accountId = reference("account_id", AccountTable)
    val avatarId = optReference("avatar_id", DocumentTable)
}