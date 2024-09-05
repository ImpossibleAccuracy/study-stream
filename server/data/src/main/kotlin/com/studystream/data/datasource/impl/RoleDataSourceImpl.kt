package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.RoleDao
import com.studystream.data.datasource.base.RoleDataSource
import com.studystream.data.model.AccountRole

class RoleDataSourceImpl : RoleDataSource {
    override suspend fun save(block: AccountRole.() -> Unit): AccountRole =
        RoleDao.new(block)

    override suspend fun getAll(): List<AccountRole> =
        RoleDao.all().toList()
}