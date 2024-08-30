package com.studystream.app.data.database.tables.refs

import com.studystream.app.data.database.tables.AccountTable
import com.studystream.app.data.database.tables.RoleTable
import org.jetbrains.exposed.sql.Table

object AccountRoleRef : Table("role_account") {
    val account = reference("account_id", AccountTable)
    val role = reference("role_id", RoleTable)
}