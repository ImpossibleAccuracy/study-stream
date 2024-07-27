package com.solovocal.domain.service

import com.solovocal.domain.model.Account

interface AuthService {
    suspend fun authUser(email: String, password: String): Result<Account>

    suspend fun findUser(email: String): Account?
}