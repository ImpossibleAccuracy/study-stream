package com.studystream.shared.payload.request

import com.studystream.shared.payload.dto.DeviceTypeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveDeviceRequest(
    @SerialName("name")
    val name: String? = null,
    @SerialName("token")
    val token: String,
    @SerialName("type")
    val type: DeviceTypeDto? = null,
)