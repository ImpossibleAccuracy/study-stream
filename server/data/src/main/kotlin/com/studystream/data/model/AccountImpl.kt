package com.studystream.data.model

import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.dao.RoleDao
import com.studystream.data.database.tables.AccountTable
import com.studystream.data.database.tables.refs.AccountRoleRef
import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import com.studystream.domain.security.Role
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.mapLazy

class AccountImpl(id: EntityID<Id>) : Account, ModelImpl(id, AccountTable) {
    override var username: String by AccountTable.username

    override var password: String by AccountTable.password

    private var _roles: SizedIterable<AccountRole> by RoleDao via AccountRoleRef
    override val roles: Iterable<Role>
        get() = _roles.mapLazy { it.role }
}