package com.studystream.data.model

import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.dao.DeviceTypeDao
import com.studystream.data.database.extensions.enums.asEnum
import com.studystream.data.database.extensions.mapper.withMapper
import com.studystream.data.database.tables.DeviceTable
import com.studystream.domain.model.Account
import com.studystream.domain.model.Device
import com.studystream.domain.model.Id
import org.jetbrains.exposed.dao.id.EntityID

class DeviceImpl(id: EntityID<Id>) : Device, ModelImpl(id, DeviceTable) {
    override var name: String? by DeviceTable.name
    override var token: String by DeviceTable.token
    override var type: Device.Type by DeviceTypeDao referencedOn DeviceTable.type asEnum DeviceTypeDao

    override var owner: Account by AccountDao.referencedOn(DeviceTable.owner).withMapper()
}
