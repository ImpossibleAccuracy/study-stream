package com.studystream.data.repository

import com.studystream.data.datasource.base.ProfileDataSource
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.model.Account
import com.studystream.domain.model.Document
import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile
import com.studystream.domain.repository.ProfileRepository
import com.studystream.domain.utils.require
import kotlinx.datetime.LocalDate

class ProfileRepositoryImpl(
    private val profileDataSource: ProfileDataSource,
) : ProfileRepository {
    override suspend fun createProfile(
        owner: Account,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate,
        avatar: Document?
    ): Result<Profile> = ioCatchingCall {
        profileDataSource.save {
            this.name = name
            this.surname = surname
            this.patronymic = patronymic
            this.birthday = birthday
            this.avatar = avatar
            this.account = owner
        }
    }

    override suspend fun getProfile(id: Id): Result<Profile> = ioCatchingCall {
        profileDataSource.findById(id).require()
    }

    override suspend fun getProfiles(): List<Profile> = ioCall {
        profileDataSource.getAll()
    }

    override suspend fun getProfilesByOwner(ownerId: Id): List<Profile> = ioCall {
        profileDataSource.findByOwnerId(ownerId)
    }

    override suspend fun existsProfile(id: Id): Boolean = ioCall {
        profileDataSource.existsById(id)
    }

    override suspend fun existsProfile(
        accountId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        excludeProfileId: Id?,
    ): Boolean =
        ioCall {
            profileDataSource.existsByFullNameAndOwner(
                accountId = accountId,
                name = name,
                surname = surname,
                patronymic = patronymic,
                excludeProfileId = excludeProfileId
            )
        }

    override suspend fun updateProfile(
        profile: Profile,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate
    ): Result<Profile> = ioCatchingCall {
        profileDataSource.save(profile.idValue) {
            this.name = name
            this.surname = surname
            this.patronymic = patronymic
            this.birthday = birthday
        }
    }

    override suspend fun updateAvatar(profile: Profile, avatar: Document?): Result<Unit> = ioCatchingCall {
        profileDataSource.save(profile.idValue) {
            this.avatar = avatar
        }
    }

    override suspend fun deleteProfile(profile: Profile): Result<Unit> = ioCatchingCall {
        profileDataSource.deleteById(profile.idValue)
    }
}