package com.studystream.app.data.service

import com.studystream.app.data.database.dao.ProfileDao
import com.studystream.app.data.database.tables.ProfileTable
import com.studystream.app.data.database.utils.exists
import com.studystream.app.data.utils.ioCall
import com.studystream.app.data.utils.ioCatchingCall
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Profile
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.domain.utils.require
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and

class ProfileServiceImpl(
    private val profileDao: ProfileDao,
) : ProfileService {
    override suspend fun createProfile(
        owner: Account,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate,
        avatar: Document?
    ): Result<Profile> = ioCatchingCall {
        profileDao.new {
            this.name = name
            this.surname = surname
            this.patronymic = patronymic
            this.birthday = birthday
            this.avatar = avatar
            this.account = owner
        }
    }

    override suspend fun getProfile(id: Id): Result<Profile> = ioCatchingCall {
        profileDao.findById(id).require()
    }

    override suspend fun getProfiles(): List<Profile> = ioCall {
        profileDao.all().toList()
    }

    override suspend fun getProfilesByOwner(ownerId: Id): List<Profile> = ioCall {
        profileDao
            .find {
                ProfileTable.accountId eq ownerId
            }
            .toList()
    }

    override suspend fun existsProfile(id: Id): Boolean = ioCall {
        profileDao.exists(ProfileTable.id eq id)
    }

    override suspend fun existsProfile(
        accountId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        excludeProfileId: Id?,
    ): Boolean =
        ioCall {
            profileDao.exists(
                (ProfileTable.accountId eq accountId) and
                        (ProfileTable.name eq name) and
                        (ProfileTable.surname eq surname) and
                        (ProfileTable.patronymic eq patronymic) and
                        (ProfileTable.id neq excludeProfileId)
            )
        }

    override suspend fun updateProfile(
        profile: Profile,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate
    ): Result<Profile> = ioCatchingCall {
        profileDao.findByIdAndUpdate(profile.idValue) {
            it.name = name
            it.surname = surname
            it.patronymic = patronymic
            it.birthday = birthday
        }!!
    }

    override suspend fun updateAvatar(profile: Profile, avatar: Document?): Result<Unit> = ioCatchingCall {
        profileDao.findByIdAndUpdate(profile.idValue) {
            it.avatar = avatar
        }
    }

    override suspend fun deleteProfile(profile: Profile): Result<Unit> = ioCatchingCall {
        profileDao.findById(profile.idValue)!!.delete()
    }
}