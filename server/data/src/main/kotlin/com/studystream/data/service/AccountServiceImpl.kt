package com.studystream.data.service

import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.tables.AccountTable
import com.studystream.data.database.utils.exists
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.exception.ResourceNotFoundException
import com.studystream.domain.service.AccountService
import com.studystream.domain.utils.require
import com.studystream.domain.model.Account
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class AccountServiceImpl(
    private val accountDao: AccountDao,
) : AccountService {
    override suspend fun createAccount(username: String, password: String): Result<Account> = ioCatchingCall {
        if (accountDao.exists(AccountTable.username eq username)) {
            throw ResourceNotFoundException("Username already used")
        }

        accountDao.new {
            this.username = username
            this.password = password
        }
    }

    override suspend fun getAccount(id: Int): Result<Account> = ioCatchingCall {
        accountDao.findById(id).require()
    }

    override suspend fun getAccount(username: String): Result<Account> = ioCatchingCall {
        accountDao
            .find { AccountTable.username eq username }
            .firstOrNull()
            .require()
    }

    override suspend fun getAccounts(): List<Account> = ioCall {
        accountDao.all().toList()
    }

    override suspend fun updateAccount(id: Int, username: String): Result<Account> = ioCatchingCall {
        if (accountDao.exists(
                (AccountTable.username eq username) and
                        (AccountTable.id neq id)
            )
        ) {
            throw ResourceNotFoundException("Username already used")
        }

        AccountTable
            .update(
                where = {
                    AccountTable.id eq id
                }
            ) {
                it[AccountTable.username] = username
            }

        accountDao[id]
    }

    override suspend fun deleteAccount(id: Int): Result<Unit> = ioCatchingCall {
        AccountTable
            .deleteWhere {
                this@deleteWhere.id eq id
            }
            .let { deletedRows ->
                if (deletedRows == 0) {
                    throw ResourceNotFoundException("Account not found")
                }
            }
    }
}