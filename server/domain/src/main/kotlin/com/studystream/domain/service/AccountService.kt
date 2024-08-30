package com.studystream.domain.service

import com.studystream.domain.model.Account

interface AccountService {
    suspend fun createAccount(
        username: String,
        password: String,
    ): Result<Account>

    suspend fun getAccount(id: Int): Result<Account>

    suspend fun getAccounts(): List<Account>

    suspend fun getAccount(username: String): Result<Account>

    suspend fun updateAccount(id: Int, username: String): Result<Account>

    suspend fun deleteAccount(id: Int): Result<Unit>
}