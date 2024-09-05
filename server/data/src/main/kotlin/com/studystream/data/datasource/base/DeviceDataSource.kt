package com.studystream.data.datasource.base

import com.studystream.domain.model.Device
import com.studystream.domain.model.Id

interface DeviceDataSource : CrudDataSource<Device> {
    suspend fun findByToken(token: String): Device?

    suspend fun search(
        ownerId: Id?,
        deviceType: Device.Type?,
    ): List<Device>
}