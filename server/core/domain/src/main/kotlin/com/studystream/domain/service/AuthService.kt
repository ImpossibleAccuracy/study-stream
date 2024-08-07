package com.studystream.domain.service

import com.studystream.domain.model.Account

interface AuthService {
    suspend fun signIn(username: String, password: String): Result<Account>

    suspend fun signUp(username: String, password: String): Result<Account>
}