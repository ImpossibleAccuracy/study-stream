package com.studystream.data.datasource.base

import com.studystream.domain.model.Id

interface CrudDataSource<T> {
    suspend fun save(block: T.() -> Unit): T

    suspend fun save(id: Id, block: T.() -> Unit): T

    suspend fun existsById(id: Id): Boolean

    suspend fun findById(id: Id): T?

    suspend fun getAll(): List<T>

    suspend fun deleteById(id: Id): Boolean
}