package com.studystream.app.data.service

import com.studystream.app.data.database.dao.DeviceDao
import com.studystream.app.data.database.tables.DeviceTable
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Device
import com.studystream.app.domain.service.DeviceService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DeviceServiceImpl : DeviceService {
    override suspend fun saveDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type
    ): Result<Device> = runCatchingTransaction {
        updateDeviceIfSaved(token, name, type)
            ?: createDevice(owner, name, token, type)
    }

    private fun updateDeviceIfSaved(
        token: String,
        name: String?,
        type: Device.Type
    ) = DeviceDao.findSingleByAndUpdate(DeviceTable.token eq token) {
        it.name = name
        it.type = type
    }

    private fun createDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type
    ) = DeviceDao.new {
        this.owner = owner
        this.name = name
        this.token = token
        this.type = type
    }
}