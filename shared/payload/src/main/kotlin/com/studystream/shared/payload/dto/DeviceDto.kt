package com.studystream.shared.payload.dto

import com.studystream.shared.payload.RemoteId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    @SerialName("id")
    val id: RemoteId,
    @SerialName("name")
    val name: String?,
    @SerialName("type")
    val type: DeviceTypeDto?,
)
