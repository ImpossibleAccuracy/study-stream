package com.studystream.app.data.database.tables

import com.studystream.app.data.database.base.BaseTable

object AccountTable : BaseTable("account") {
    val username = varchar("username", 255)
    val password = varchar("password", 255)
}