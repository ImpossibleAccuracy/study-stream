package com.studystream.app.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object DeviceTable : IntIdTable("device") {
    val owner = reference("account_id", AccountTable)
    val name = varchar("name", 255).nullable()
    val token = varchar("token", 255)
    val type = reference("type_id", DeviceTypeTable)
}