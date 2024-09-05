package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.DeviceDao
import com.studystream.data.database.dao.DeviceTypeDao
import com.studystream.data.database.tables.DeviceTable
import com.studystream.data.database.tables.DeviceTypeTable
import com.studystream.data.database.utils.findSingle
import com.studystream.data.datasource.base.DeviceDataSource
import com.studystream.data.model.DeviceImpl
import com.studystream.domain.model.Device
import com.studystream.domain.model.Id
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andIfNotNull

class DeviceDataSourceImpl : DeviceDataSource, CrudDataSourceImpl<Device, DeviceImpl>(DeviceDao) {
    override suspend fun findByToken(token: String): Device? =
        DeviceDao.findSingle(DeviceTable.token eq token)

    override suspend fun search(ownerId: Id?, deviceType: Device.Type?): List<Device> {
        val typeEntity = deviceType?.let {
            DeviceTypeDao.find { DeviceTypeTable.title eq it }.firstOrNull()
        }

        return DeviceDao
            .find {
                Op.TRUE
                    .andIfNotNull(
                        ownerId?.let {
                            DeviceTable.owner eq it
                        }
                    )
                    .andIfNotNull(
                        typeEntity?.id?.let {
                            DeviceTable.type eq it.value
                        }
                    )
            }
            .toList()
    }
}