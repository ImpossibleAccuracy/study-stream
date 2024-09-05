package com.studystream.domain.repository

import com.studystream.domain.model.Account

interface AuthRepository {
    suspend fun signIn(username: String, password: String): Result<Account>

    suspend fun signUp(username: String, password: String): Result<Account>
}