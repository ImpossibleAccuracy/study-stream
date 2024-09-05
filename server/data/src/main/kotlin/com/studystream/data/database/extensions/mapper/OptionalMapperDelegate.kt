package com.studystream.data.database.extensions.mapper

import com.studystream.data.database.base.ModelImpl
import com.studystream.domain.model.Id
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.OptionalReference
import org.jetbrains.exposed.dao.id.EntityID
import kotlin.reflect.KProperty

class OptionalMapperDelegate<T, E : ModelImpl>(
    private val reference: OptionalReference<EntityID<Id>, Id, E>,
    private val mapper: DomainToDataMapper<T, E>
) {
    operator fun getValue(thisRef: Entity<Id>, property: KProperty<*>): T? = with(thisRef) {
        reference.getValue(thisRef, property)?.let { mapper.toDomain(it) }
    }

    operator fun setValue(thisRef: Entity<Id>, property: KProperty<*>, value: T?) = with(thisRef) {
        reference.setValue(thisRef, property, value?.let { mapper.toData(it) })
    }
}