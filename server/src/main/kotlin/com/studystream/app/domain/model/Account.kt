package com.studystream.app.domain.model

import com.studystream.app.data.database.tables.AccountTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

class Account(id: EntityID<Int>) : IntEntity(id) {
    var username: String by AccountTable.username
    var password: String by AccountTable.password
}
