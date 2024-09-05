package com.studystream.data.datasource.base

import com.studystream.domain.model.Account
import com.studystream.domain.model.Id

interface AccountDataSource : CrudDataSource<Account> {
    suspend fun existsByUsername(username: String): Boolean

    suspend fun existsByUsernameEqAndIdNotEq(username: String, id: Id): Boolean

    suspend fun findByUsername(username: String): Account?
}