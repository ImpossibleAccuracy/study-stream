package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.ProfileDao
import com.studystream.data.database.tables.ProfileTable
import com.studystream.data.database.utils.exists
import com.studystream.data.datasource.base.ProfileDataSource
import com.studystream.data.model.ProfileImpl
import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and

class ProfileDataSourceImpl : ProfileDataSource, CrudDataSourceImpl<Profile, ProfileImpl>(ProfileDao) {
    override suspend fun existsByFullNameAndOwner(
        accountId: Id,
        name: String,
        surname: String,
        patronymic: String?,
        excludeProfileId: Id?
    ): Boolean = ProfileDao.exists(
        (ProfileTable.accountId eq accountId) and
                (ProfileTable.name eq name) and
                (ProfileTable.surname eq surname) and
                (ProfileTable.patronymic eq patronymic) and
                (ProfileTable.id neq excludeProfileId)
    )

    override suspend fun findByOwnerId(ownerId: Id): List<Profile> =
        ProfileDao
            .find(ProfileTable.accountId eq ownerId)
            .toList()
}