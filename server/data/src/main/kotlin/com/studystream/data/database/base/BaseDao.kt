package com.studystream.data.database.base

import com.studystream.data.utils.now
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.toEntity
import kotlin.reflect.KClass

abstract class BaseDao<T : ModelImpl>(
    table: BaseTable,
    modelClass: KClass<T>
) : IntEntityClass<T>(table, modelClass.java) {
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = LocalDateTime.now()
                } catch (_: Exception) {
                }
            }
        }
    }
}
