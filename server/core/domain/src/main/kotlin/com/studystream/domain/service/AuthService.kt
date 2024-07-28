package com.studystream.domain.service

import com.studystream.domain.model.Account

interface AuthService {
    suspend fun authUser(email: String, password: String): Result<Account>

    suspend fun findUser(email: String): Account?
}