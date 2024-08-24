package com.studystream.app.data.database.tables

import com.studystream.app.data.database.base.BaseTable

object DeviceTable : BaseTable("device") {
    val owner = reference("account_id", AccountTable)
    val name = varchar("name", 255).nullable()
    val token = varchar("token", 255)
    val type = reference("type_id", DeviceTypeTable)
}