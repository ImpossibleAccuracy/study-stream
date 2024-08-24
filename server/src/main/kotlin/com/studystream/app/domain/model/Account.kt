package com.studystream.app.domain.model

import com.studystream.app.data.database.base.BaseModel
import com.studystream.app.data.database.tables.AccountTable
import org.jetbrains.exposed.dao.id.EntityID

class Account(id: EntityID<Id>) : BaseModel(id, AccountTable) {
    var username: String by AccountTable.username
    var password: String by AccountTable.password
}
