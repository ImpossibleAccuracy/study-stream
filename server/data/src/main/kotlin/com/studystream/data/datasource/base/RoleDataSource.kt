package com.studystream.data.datasource.base

import com.studystream.data.model.AccountRole

interface RoleDataSource {
    suspend fun save(block: AccountRole.() -> Unit): AccountRole

    suspend fun getAll(): List<AccountRole>
}