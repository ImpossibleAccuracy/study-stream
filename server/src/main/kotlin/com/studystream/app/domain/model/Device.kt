package com.studystream.app.domain.model

import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.data.database.dao.DeviceTypeDao
import com.studystream.app.data.database.tables.DeviceTable
import com.studystream.app.data.database.tables.DeviceTypeTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

class Device(id: EntityID<Id>) : IntEntity(id) {
    var owner: Account by AccountDao referencedOn DeviceTable.owner
    var name: String? by DeviceTable.name
    var token: String by DeviceTable.token

    private var _type: DeviceTypeDao by DeviceTypeDao referencedOn DeviceTable.type
    var type: Type
        get() = _type.title
        set(value) {
            _type = DeviceTypeDao
                .find {
                    DeviceTypeTable.title eq value
                }
                .first()
        }

    enum class Type {
        ANDROID,
        IOS,
        WINDOWS,
        WEB,
        NOT_SPECIFIED;
    }
}