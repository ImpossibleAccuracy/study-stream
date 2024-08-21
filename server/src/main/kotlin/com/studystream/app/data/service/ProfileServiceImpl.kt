package com.studystream.app.data.service

import com.studystream.app.data.database.dao.ProfileDao
import com.studystream.app.data.database.tables.ProfileTable
import com.studystream.app.data.database.utils.exists
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Profile
import com.studystream.app.domain.service.ProfileService
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and

class ProfileServiceImpl : ProfileService {
    override suspend fun createProfile(
        owner: Account,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate,
        avatar: Document?
    ): Result<Profile> = runCatchingTransaction {
        ProfileDao.new {
            this.name = name
            this.surname = surname
            this.patronymic = patronymic
            this.birthday = birthday
            this.avatarId = avatar?.id
            this.account = owner
        }
    }

    override suspend fun getProfile(id: Id): Profile? = runSuspendedTransaction {
        ProfileDao.findById(id)
    }

    override suspend fun getProfilesByOwner(ownerId: Id): List<Profile> = runSuspendedTransaction {
        ProfileDao
            .find {
                ProfileTable.accountId eq ownerId
            }
            .toList()
    }

    override suspend fun existsProfile(id: Id): Boolean = runSuspendedTransaction {
        ProfileDao.exists(ProfileTable.id eq id)
    }

    override suspend fun existsProfile(
        accountId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        excludeProfileId: Id?,
    ): Boolean =
        runSuspendedTransaction {
            ProfileDao.exists(
                (ProfileTable.accountId eq accountId) and
                        (ProfileTable.name eq name) and
                        (ProfileTable.surname eq surname) and
                        (ProfileTable.patronymic eq patronymic) and
                        (ProfileTable.id neq excludeProfileId)
            )
        }

    override suspend fun updateProfile(
        profileId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate
    ): Result<Profile> = runCatchingTransaction {
        ProfileDao.findByIdAndUpdate(profileId) {
            it.name = name
            it.surname = surname
            it.patronymic = patronymic
            it.birthday = birthday
        }!!
    }

    override suspend fun updateAvatar(profileId: Id, avatar: Document?): Result<Unit> = runCatchingTransaction {
        ProfileDao.findByIdAndUpdate(profileId) {
            it.avatarId = avatar?.id
        }
    }

    override suspend fun deleteProfile(profileId: Id): Result<Unit> = runCatchingTransaction {
        ProfileDao.findById(profileId)!!.delete()
    }
}