package com.solovocal.data.service

import com.solovocal.domain.model.Account
import com.solovocal.domain.service.AuthService

class AuthServiceImpl : AuthService {
    override suspend fun authUser(email: String, password: String): Result<Account> = kotlin.runCatching {
        Account(
            email,
        )
    }

    override suspend fun findUser(email: String): Account? {
        return Account(
            email
        )
    }
}