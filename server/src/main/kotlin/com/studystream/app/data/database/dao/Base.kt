package com.studystream.app.data.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.IntIdTable

inline fun <reified M : IntEntity, T : IntIdTable> buildDao(table: T) =
    object : IntEntityClass<M>(table, M::class.java) {}
