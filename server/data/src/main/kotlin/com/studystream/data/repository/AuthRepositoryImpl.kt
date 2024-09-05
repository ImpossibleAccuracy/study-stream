package com.studystream.data.repository

import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.model.Account
import com.studystream.domain.repository.AccountRepository
import com.studystream.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val accountRepository: AccountRepository,
    private val passwordManager: PasswordManager,
) : AuthRepository {
    override suspend fun signIn(username: String, password: String): Result<Account> = ioCatchingCall {
        val account = accountRepository.getAccount(username).getOrThrow()

        if (!passwordManager.match(password, account.password)) {
            throw OperationRejectedException("Password mismatch")
        }

        return@ioCatchingCall account
    }

    override suspend fun signUp(username: String, password: String): Result<Account> =
        accountRepository
            .createAccount(
                username = username,
                password = passwordManager.encrypt(password),
            )
}