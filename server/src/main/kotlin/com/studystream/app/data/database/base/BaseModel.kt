package com.studystream.app.data.database.base

import com.studystream.app.domain.model.Id
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

abstract class BaseModel(id: EntityID<Id>, table: BaseTable) : IntEntity(id) {
    var idValue: Id
        get() = id.value
        set(value) {
            id._value = value
        }

    val createdAt: LocalDateTime by table.createdAt
    var updatedAt: LocalDateTime? by table.updatedAt
}