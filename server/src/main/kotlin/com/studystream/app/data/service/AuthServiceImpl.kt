package com.studystream.app.data.service

import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.data.database.tables.AccountTable
import com.studystream.app.data.database.utils.exists
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.mapper.toDomain
import com.studystream.app.domain.exception.OperationRejectedException
import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import com.studystream.app.domain.service.AuthService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AuthServiceImpl(
    private val accountService: AccountService,
    private val passwordManager: PasswordManager,
) : AuthService {
    override suspend fun signIn(username: String, password: String): Result<Account> = runCatching {
        val account = accountService.findUser(username)
            ?: throw ResourceNotFoundException("Account not found")

        if (!passwordManager.match(password, account.password)) {
            throw OperationRejectedException("Password mismatch")
        }

        return@runCatching account
    }

    override suspend fun signUp(username: String, password: String): Result<Account> = runCatchingTransaction {
        if (AccountDao.exists(AccountTable.username eq username)) {
            throw ResourceNotFoundException("Username already used")
        }

        val encryptedPassword = passwordManager.encrypt(password)

        AccountDao
            .new {
                this.username = username
                this.password = encryptedPassword
            }
            .toDomain()
    }
}