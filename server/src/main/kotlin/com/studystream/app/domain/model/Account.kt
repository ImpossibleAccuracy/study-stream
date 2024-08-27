package com.studystream.app.domain.model

import com.studystream.app.data.database.base.BaseModel
import com.studystream.app.data.database.dao.RoleDao
import com.studystream.app.data.database.tables.AccountTable
import com.studystream.app.data.database.tables.refs.AccountRole
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

class Account(id: EntityID<Id>) : BaseModel(id, AccountTable) {
    var username: String by AccountTable.username
    var password: String by AccountTable.password

    var roles: SizedIterable<Role> by RoleDao via AccountRole
}
