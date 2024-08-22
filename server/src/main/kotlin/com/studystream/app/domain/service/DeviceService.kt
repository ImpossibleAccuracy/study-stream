package com.studystream.app.domain.service

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Device

interface DeviceService {
    suspend fun saveDevice(
        owner: Account,
        name: String?,
        token: String,
        type: Device.Type,
    ): Result<Device>
}