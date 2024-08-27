package com.studystream.app.domain.model

import com.studystream.app.data.database.base.BaseModel
import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.data.database.dao.DeviceTypeDao
import com.studystream.app.data.database.extensions.enums.asEnum
import com.studystream.app.data.database.tables.DeviceTable
import org.jetbrains.exposed.dao.id.EntityID

class Device(id: EntityID<Id>) : BaseModel(id, DeviceTable) {
    var owner: Account by AccountDao referencedOn DeviceTable.owner
    var name: String? by DeviceTable.name
    var token: String by DeviceTable.token
    var type: Type by DeviceTypeDao referencedOn DeviceTable.type asEnum DeviceTypeDao

    enum class Type {
        ANDROID,
        IOS,
        WINDOWS,
        WEB,
        NOT_SPECIFIED;

        fun nullIfNotSpecified(): Type? = when (this) {
            NOT_SPECIFIED -> null
            else -> this
        }
    }
}
