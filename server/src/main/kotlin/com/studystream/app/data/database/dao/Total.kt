package com.studystream.app.data.database.dao

import com.studystream.app.data.database.tables.*
import com.studystream.app.data.database.tables.base.EnumTable
import com.studystream.app.domain.model.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

inline fun <reified M : IntEntity, T : IntIdTable> buildDao(table: T) =
    object : IntEntityClass<M>(table, M::class.java) {}

val AccountDao = buildDao<Account, AccountTable>(AccountTable)
val ProfileDao = buildDao<Profile, ProfileTable>(ProfileTable)
val DocumentDao = buildDao<Document, DocumentTable>(DocumentTable)
val DocumentTypeDao = buildDao<Document.Type, DocumentTypeTable>(DocumentTypeTable)
val DeviceDao = buildDao<Device, DeviceTable>(DeviceTable)
