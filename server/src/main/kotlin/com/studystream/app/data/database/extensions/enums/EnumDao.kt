package com.studystream.app.data.database.extensions.enums

import com.studystream.app.domain.model.Id
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import kotlin.reflect.KClass

abstract class EnumDao<E : Enum<E>>(id: EntityID<Id>, table: EnumTable<E>) : IntEntity(id) {
    var title: E by table.title
}

abstract class EnumDaoCompanion<E : Enum<E>, out M : EnumDao<E>>(
    table: EnumTable<E>,
    entityType: KClass<M>,
) : EntityClass<Int, M>(table, entityType.java, null)
