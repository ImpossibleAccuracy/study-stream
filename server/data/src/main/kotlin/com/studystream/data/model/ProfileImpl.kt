package com.studystream.data.model

import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.dao.DocumentDao
import com.studystream.data.database.extensions.mapper.withMapper
import com.studystream.data.database.tables.ProfileTable
import com.studystream.domain.model.Account
import com.studystream.domain.model.Document
import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.dao.id.EntityID

class ProfileImpl(id: EntityID<Id>) : Profile, ModelImpl(id, ProfileTable) {
    override var name: String by ProfileTable.name
    override var surname: String by ProfileTable.surname
    override var patronymic: String? by ProfileTable.patronymic
    override var birthday: LocalDate by ProfileTable.birthday

    override var account: Account by AccountDao.referencedOn(ProfileTable.accountId).withMapper()
    override var avatar: Document? by DocumentDao.optionalReferencedOn(ProfileTable.avatarId).withMapper()
}