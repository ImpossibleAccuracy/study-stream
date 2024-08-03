package com.studystream.domain.service

import com.studystream.domain.model.Account

interface AccountService {
    suspend fun findUser(id: Int): Account?

    suspend fun findUser(username: String): Account?

    suspend fun updateUser(id: Int, username: String): Result<Account>

    suspend fun deleteUser(id: Int): Result<Unit>
}