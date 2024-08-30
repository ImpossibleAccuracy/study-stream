package com.studystream.app.domain.model

import com.studystream.app.data.database.dao.PrivilegeDao
import com.studystream.app.data.database.tables.RoleTable
import com.studystream.app.data.database.tables.refs.RolePrivilegesRef
import com.studystream.app.domain.security.Role
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

class AccountRole(id: EntityID<Id>) : IntEntity(id) {
    var title by RoleTable.title

    var role: Role
        get() = Role.entries.first { it.roleName == title }
        set(value) {
            title = value.roleName
        }

    var privileges by PrivilegeDao via RolePrivilegesRef
}
