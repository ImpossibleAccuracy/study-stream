package com.studystream.data.database.dao

import com.studystream.data.database.extensions.enums.EnumDao
import com.studystream.data.database.extensions.enums.EnumDaoCompanion
import com.studystream.data.database.tables.DeviceTypeTable
import com.studystream.domain.model.Device
import com.studystream.domain.model.Id
import org.jetbrains.exposed.dao.id.EntityID

class DeviceTypeDaoModel(id: EntityID<Id>) : EnumDao<Device.Type>(id, DeviceTypeTable)
object DeviceTypeDao : EnumDaoCompanion<Device.Type, DeviceTypeDaoModel>(DeviceTypeTable, DeviceTypeDaoModel::class)
