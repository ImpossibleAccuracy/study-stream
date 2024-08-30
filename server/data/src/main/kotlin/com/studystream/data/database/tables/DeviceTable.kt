package com.studystream.data.database.tables

import com.studystream.data.database.base.BaseTable

object DeviceTable : BaseTable("ticket") {
    val owner = reference("account_id", AccountTable)
    val name = varchar("name", 255).nullable()
    val token = varchar("token", 255)
    val type = reference("type_id", DeviceTypeTable)
}