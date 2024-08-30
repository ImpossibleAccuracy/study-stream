package com.studystream.data.database.tables.refs

import com.studystream.data.database.tables.PrivilegeTable
import com.studystream.data.database.tables.RoleTable
import org.jetbrains.exposed.sql.Table

object RolePrivilegesRef : Table("privilege_role") {
    val role = reference("role_id", RoleTable)
    val privilege = reference("privilege_id", PrivilegeTable)
}