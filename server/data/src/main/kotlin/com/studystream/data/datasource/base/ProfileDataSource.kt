package com.studystream.data.datasource.base

import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile

interface ProfileDataSource : CrudDataSource<Profile> {
    suspend fun existsByFullNameAndOwner(
        accountId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        excludeProfileId: Id?,
    ): Boolean

    suspend fun findByOwnerId(ownerId: Id): List<Profile>
}