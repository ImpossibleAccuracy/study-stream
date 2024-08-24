package com.studystream.app.data.service

import com.studystream.app.data.database.dao.DeviceDao
import com.studystream.app.data.database.dao.DeviceTypeDao
import com.studystream.app.data.database.tables.DeviceTable
import com.studystream.app.data.database.tables.DeviceTypeTable
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Device
import com.studystream.app.domain.service.DeviceService
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andIfNotNull

class DeviceServiceImpl(
    private val deviceDao: DeviceDao,
    private val deviceTypeDao: DeviceTypeDao,
) : DeviceService {
    override suspend fun saveDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type
    ): Result<Device> = runCatchingTransaction {
        updateDeviceIfSaved(token, name, type)
            ?: createDevice(owner, name, token, type)
    }

    override suspend fun getDevices(filters: DeviceService.Filters): List<Device> = runSuspendedTransaction {
        val type = filters.deviceType?.let { type ->
            deviceTypeDao.find { DeviceTypeTable.title eq type }.firstOrNull()
        }

        deviceDao
            .find {
                Op.TRUE
                    .andIfNotNull(
                        filters.ownerId?.let {
                            DeviceTable.owner eq it
                        }
                    )
                    .andIfNotNull(
                        type?.id?.let {
                            DeviceTable.type eq it
                        }
                    )
            }
            .toList()
    }

    private fun updateDeviceIfSaved(
        token: String,
        name: String?,
        type: Device.Type
    ) = deviceDao.findSingleByAndUpdate(DeviceTable.token eq token) {
        it.name = name
        it.type = type
    }

    private fun createDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type
    ) = deviceDao.new {
        this.owner = owner
        this.name = name
        this.token = token
        this.type = type
    }
}