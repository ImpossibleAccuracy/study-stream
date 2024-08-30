package com.studystream.data.service

import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService
import com.studystream.domain.service.AuthService

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