package com.studystream.app.domain.model

import com.studystream.app.data.database.base.BaseModel
import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.data.database.dao.DocumentDao
import com.studystream.app.data.database.tables.ProfileTable
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.dao.id.EntityID

class Profile(id: EntityID<Id>) : BaseModel(id, ProfileTable) {
    var name: String by ProfileTable.name
    var surname: String by ProfileTable.surname
    var patronymic: String? by ProfileTable.patronymic
    var birthday: LocalDate by ProfileTable.birthday
    var account: Account by AccountDao referencedOn ProfileTable.accountId
    var avatar: Document? by DocumentDao optionalReferencedOn ProfileTable.avatarId
}
