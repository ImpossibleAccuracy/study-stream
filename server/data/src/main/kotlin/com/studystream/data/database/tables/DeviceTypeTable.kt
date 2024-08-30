package com.studystream.data.database.tables

import com.studystream.data.database.extensions.enums.EnumTable
import com.studystream.domain.model.Device

object DeviceTypeTable : EnumTable<Device.Type>(
    tableName = "device_type",
    enum = Device.Type::class,
)
