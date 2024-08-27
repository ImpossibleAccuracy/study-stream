package com.studystream.app.data.service

import com.studystream.app.data.utils.ioCatchingCall
import com.studystream.app.domain.exception.OperationRejectedException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import com.studystream.app.domain.service.AuthService

class AuthServiceImpl(
    private val accountService: AccountService,
    private val passwordManager: PasswordManager,
) : AuthService {
    override suspend fun signIn(username: String, password: String): Result<Account> = ioCatchingCall {
        val account = accountService.getAccount(username).getOrThrow()

        if (!passwordManager.match(password, account.password)) {
            throw OperationRejectedException("Password mismatch")
        }

        return@ioCatchingCall account
    }

    override suspend fun signUp(username: String, password: String): Result<Account> =
        accountService
            .createAccount(
                username = username,
                password = passwordManager.encrypt(password),
            )
}