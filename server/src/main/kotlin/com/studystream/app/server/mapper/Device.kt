package com.studystream.app.server.mapper

import com.studystream.app.domain.model.Device
import com.studystream.shared.payload.dto.DeviceDto
import com.studystream.shared.payload.dto.DeviceTypeDto

fun Device.toDto() = DeviceDto(
    id = id.value,
    name = name,
    type = type.toDto()
)

fun Device.Type.toDto(): DeviceTypeDto? = when (this) {
    Device.Type.ANDROID -> DeviceTypeDto.ANDROID
    Device.Type.IOS -> DeviceTypeDto.IOS
    Device.Type.WINDOWS -> DeviceTypeDto.WINDOWS
    Device.Type.WEB -> DeviceTypeDto.WEB
    Device.Type.NOT_SPECIFIED -> null
}

fun DeviceTypeDto?.fromDto(): Device.Type = when (this) {
    DeviceTypeDto.ANDROID -> Device.Type.ANDROID
    DeviceTypeDto.IOS -> Device.Type.IOS
    DeviceTypeDto.WINDOWS -> Device.Type.WINDOWS
    DeviceTypeDto.WEB -> Device.Type.WEB
    null -> Device.Type.NOT_SPECIFIED
}