package com.studystream.domain.repository

import com.studystream.domain.model.Account
import com.studystream.domain.model.Document
import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile
import kotlinx.datetime.LocalDate

interface ProfileRepository {
    suspend fun createProfile(
        owner: Account,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate,
        avatar: Document?,
    ): Result<Profile>

    suspend fun getProfile(id: Id): Result<Profile>

    suspend fun getProfiles(): List<Profile>

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
        profile: Profile,
        name: String,
        surname: String,
        patronymic: String?,
        birthday: LocalDate,
    ): Result<Profile>

    suspend fun updateAvatar(profile: Profile, avatar: Document?): Result<Unit>

    suspend fun deleteProfile(profile: Profile): Result<Unit>
}