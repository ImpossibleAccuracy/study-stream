package com.studystream.data.database.extensions.mapper

import com.studystream.data.database.base.ModelImpl
import com.studystream.domain.model.Id
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.Reference
import org.jetbrains.exposed.dao.id.EntityID
import kotlin.reflect.KProperty

class MapperDelegate<T, E : ModelImpl>(
    private val reference: Reference<EntityID<Id>, Id, E>,
    private val mapper: DomainToDataMapper<T, E>
) {
    operator fun getValue(thisRef: Entity<Id>, property: KProperty<*>): T = with(thisRef) {
        mapper.toDomain(reference.getValue(thisRef, property))
    }

    operator fun setValue(thisRef: Entity<Id>, property: KProperty<*>, value: T) = with(thisRef) {
        reference.setValue(thisRef, property, mapper.toData(value))
    }
}
