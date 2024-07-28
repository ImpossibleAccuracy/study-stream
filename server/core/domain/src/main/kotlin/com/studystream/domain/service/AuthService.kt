package com.studystream.domain.service

import com.studystream.domain.model.Account

interface AuthService {
    suspend fun signIn(email: String, password: String): Result<Account>
}