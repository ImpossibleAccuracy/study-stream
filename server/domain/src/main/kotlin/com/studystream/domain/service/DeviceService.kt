package com.studystream.domain.service

import com.studystream.domain.model.Account
import com.studystream.domain.model.Device
import com.studystream.domain.model.Id

interface DeviceService {
    suspend fun saveDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type,
    ): Result<Device>

    suspend fun getDevices(filters: Filters): List<Device>

    data class Filters(
        val ownerId: Id? = null,
        val deviceType: Device.Type? = null,
    )
}