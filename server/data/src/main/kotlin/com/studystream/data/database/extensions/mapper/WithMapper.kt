package com.studystream.data.database.extensions.mapper

import com.studystream.data.database.base.ModelImpl
import com.studystream.domain.model.Id
import org.jetbrains.exposed.dao.OptionalReference
import org.jetbrains.exposed.dao.Reference
import org.jetbrains.exposed.dao.id.EntityID

fun <T, E : ModelImpl> Reference<EntityID<Id>, Id, E>.withMapper(
    mapper: DomainToDataMapper<T, E> = DefaultDomainToDataMapper()
) = MapperDelegate(this, mapper)

fun <T, E : ModelImpl> OptionalReference<EntityID<Id>, Id, E>.withMapper(
    mapper: DomainToDataMapper<T, E> = DefaultDomainToDataMapper()
) = OptionalMapperDelegate(this, mapper)
