package com.studystream.data.model

import com.studystream.data.database.tables.PrivilegeTable
import com.studystream.domain.model.Id
import com.studystream.domain.security.Permission
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

class Privilege(id: EntityID<Id>) : IntEntity(id) {
    var title by PrivilegeTable.title

    var permission: Permission
        get() = Permission.entries.first { it.privilegeName == title }
        set(value) {
            title = value.privilegeName
        }
}
