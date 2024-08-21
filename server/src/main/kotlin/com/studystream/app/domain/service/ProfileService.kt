package com.studystream.app.domain.service

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Profile
import kotlinx.datetime.LocalDate

interface ProfileService {
    suspend fun createProfile(
        owner: Account,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate,
        avatar: Document?,
    ): Result<Profile>

    suspend fun getProfile(id: Id): Profile?

    suspend fun getProfilesByOwner(ownerId: Id): List<Profile>

    suspend fun existsProfile(id: Id): Boolean

    suspend fun existsProfile(
        accountId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        excludeProfileId: Id? = null,
    ): Boolean

    suspend fun updateProfile(
        profileId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate,
    ): Result<Profile>

    suspend fun updateAvatar(profileId: Id, avatar: Document?): Result<Unit>

    suspend fun deleteProfile(profileId: Id): Result<Unit>
}