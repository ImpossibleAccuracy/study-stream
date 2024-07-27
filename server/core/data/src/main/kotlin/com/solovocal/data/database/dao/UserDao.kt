package com.solovocal.data.database.dao

import com.solovocal.data.database.tables.UserTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDao>(UserTable)

    var name by UserTable.name
    var avatarId by UserTable.avatarId
}