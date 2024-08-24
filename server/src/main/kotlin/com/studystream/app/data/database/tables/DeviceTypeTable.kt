package com.studystream.app.data.database.tables

import com.studystream.app.data.database.extensions.enums.EnumTable
import com.studystream.app.domain.model.Device

object DeviceTypeTable : EnumTable<Device.Type>(
    tableName = "device_type",
    enum = Device.Type::class,
)
