package com.studystream.app.domain.service

import com.studystream.app.domain.model.Account

interface AuthService {
    suspend fun signIn(username: String, password: String): Result<Account>

    suspend fun signUp(username: String, password: String): Result<Account>
}