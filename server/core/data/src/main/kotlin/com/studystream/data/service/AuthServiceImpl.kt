package com.studystream.data.service

import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.exception.ResourceNotFoundException
import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService
import com.studystream.domain.service.AuthService

class AuthServiceImpl(
    private val accountService: AccountService,
    private val passwordManager: PasswordManager,
) : AuthService {
    override suspend fun signIn(email: String, password: String): Result<Account> = runCatching {
        val account = accountService.findUser(email)
            ?: throw ResourceNotFoundException("Account not found")

        if (!passwordManager.match(password, account.password)) {
            throw OperationRejectedException("Password mismatch")
        }

        return@runCatching account
    }
}