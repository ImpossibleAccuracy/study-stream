package com.studystream.app.data.database.dao

import com.studystream.app.data.database.tables.DeviceTypeTable
import com.studystream.app.domain.model.Device
import com.studystream.app.domain.model.Id
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DeviceTypeDao(id: EntityID<Id>) : IntEntity(id) {
    companion object : IntEntityClass<DeviceTypeDao>(DeviceTypeTable)

    val title: Device.Type by DeviceTypeTable.title
}