package com.studystream.data.repository

import com.studystream.data.datasource.base.DeviceDataSource
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.model.Account
import com.studystream.domain.model.Device
import com.studystream.domain.repository.DeviceRepository

class DeviceRepositoryImpl(
    private val deviceDataSource: DeviceDataSource,
) : DeviceRepository {
    override suspend fun saveDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type
    ): Result<Device> = ioCatchingCall {
        updateDeviceIfSaved(token, name, type)
            ?: createDevice(owner, name, token, type)
    }

    override suspend fun getDevices(filters: DeviceRepository.Filters): List<Device> = ioCall {
        deviceDataSource.search(filters.ownerId, filters.deviceType)
    }

    private suspend fun updateDeviceIfSaved(
        token: String,
        name: String?,
        type: Device.Type
    ): Device? = deviceDataSource
        .findByToken(token)
        ?.let {
            deviceDataSource.save(it.idValue) {
                this.name = name
                this.type = type
            }
        }

    private suspend fun createDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type
    ): Device = deviceDataSource.save {
        this.owner = owner
        this.name = name
        this.token = token
        this.type = type
    }
}