package com.studystream.app.data.database.base

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.toEntity
import kotlin.reflect.KClass

abstract class BaseDao<T : BaseModel>(
    table: BaseTable,
    modelClass: KClass<T>
) : IntEntityClass<T>(table, modelClass.java) {
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = now().toLocalDateTime(TimeZone.UTC)
                } catch (_: Exception) {
                }
            }
        }
    }
}
