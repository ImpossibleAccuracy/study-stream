package com.studystream.app.data.database.tables.refs

import com.studystream.app.data.database.tables.PrivilegeTable
import com.studystream.app.data.database.tables.RoleTable
import org.jetbrains.exposed.sql.Table

object RolePrivilegesRef : Table("privilege_role") {
    val role = reference("role_id", RoleTable)
    val privilege = reference("privilege_id", PrivilegeTable)
}