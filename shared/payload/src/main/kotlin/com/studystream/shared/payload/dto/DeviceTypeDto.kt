package com.studystream.shared.payload.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DeviceTypeDto {
    @SerialName("android")
    ANDROID,

    @SerialName("ios")
    IOS,

    @SerialName("windows")
    WINDOWS,

    @SerialName("web")
    WEB;
}