package com.studystream.app.data.database.dao

import com.studystream.app.data.database.tables.AccountTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AccountDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountDao>(AccountTable)

    var username by AccountTable.username
    var password by AccountTable.password
}