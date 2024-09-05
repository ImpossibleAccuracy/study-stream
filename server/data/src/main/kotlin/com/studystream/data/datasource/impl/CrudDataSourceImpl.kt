package com.studystream.data.datasource.impl

import com.studystream.data.database.base.BaseDao
import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.utils.exists
import com.studystream.data.datasource.base.CrudDataSource
import com.studystream.data.exceptions.EntityNotFoundException
import com.studystream.domain.model.BaseModel
import com.studystream.domain.model.Id
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

@Suppress("UNCHECKED_CAST")
abstract class CrudDataSourceImpl<T : BaseModel, I : ModelImpl>(
    private val dao: BaseDao<I>,
) : CrudDataSource<T> {
    override suspend fun save(block: T.() -> Unit): T =
        dao.new {
            block(this as T)
        } as T

    override suspend fun save(id: Id, block: T.() -> Unit): T {
        val entity = dao
            .findByIdAndUpdate(id) {
                block(it as T)
            } as T?

        return entity ?: throw EntityNotFoundException()
    }

    override suspend fun existsById(id: Id): Boolean =
        dao.exists(dao.table.id eq id)

    override suspend fun findById(id: Id): T? =
        dao.findById(id) as T?

    override suspend fun getAll(): List<T> =
        dao.all().toList() as List<T>

    override suspend fun deleteById(id: Id): Boolean =
        dao.table.deleteWhere {
            dao.table.id eq id
        } > 0
}