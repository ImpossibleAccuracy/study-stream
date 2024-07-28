package com.studystream.data.service

import com.studystream.domain.model.Account
import com.studystream.domain.service.AuthService

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