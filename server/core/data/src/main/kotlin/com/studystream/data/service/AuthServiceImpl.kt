package com.studystream.data.service

import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.tables.AccountTable
import com.studystream.data.database.utils.exists
import com.studystream.data.database.utils.runCatchingTransaction
import com.studystream.data.mapper.toDomain
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.exception.ResourceNotFoundException
import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService
import com.studystream.domain.service.AuthService
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