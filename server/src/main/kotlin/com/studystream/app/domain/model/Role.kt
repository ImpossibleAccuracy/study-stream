package com.studystream.app.domain.model

import com.studystream.app.data.database.dao.PrivilegeDao
import com.studystream.app.data.database.tables.RoleTable
import com.studystream.app.data.database.tables.refs.RolePrivileges
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

class Role(id: EntityID<Id>) : IntEntity(id) {
    var title by RoleTable.title

    var privileges by PrivilegeDao via RolePrivileges
}
