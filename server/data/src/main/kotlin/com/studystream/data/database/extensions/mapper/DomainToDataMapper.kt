package com.studystream.data.database.extensions.mapper

import com.studystream.data.database.base.ModelImpl

interface DomainToDataMapper<T, E : ModelImpl> {
    fun toDomain(entity: E): T
    fun toData(model: T): E
}

class DefaultDomainToDataMapper<T, E : ModelImpl> : DomainToDataMapper<T, E> {
    override fun toDomain(entity: E): T {
        @Suppress("UNCHECKED_CAST")
        return entity as T
    }

    override fun toData(model: T): E {
        @Suppress("UNCHECKED_CAST")
        return model as E
    }
}