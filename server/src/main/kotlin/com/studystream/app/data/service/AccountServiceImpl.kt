package com.studystream.app.data.service

import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.data.database.tables.AccountTable
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import com.studystream.app.domain.utils.require
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class AccountServiceImpl(
    private val accountDao: AccountDao,
) : AccountService {
    override suspend fun createAccount(username: String, password: String): Result<Account> = runCatchingTransaction {
        accountDao.new {
            this.username = username
            this.password = password
        }
    }

    override suspend fun getAccount(id: Int): Result<Account> = runCatchingTransaction {
        accountDao.findById(id).require()
    }

    override suspend fun getAccount(username: String): Result<Account> = runCatchingTransaction {
        accountDao
            .find { AccountTable.username eq username }
            .firstOrNull()
            .require()
    }

    override suspend fun updateAccount(id: Int, username: String): Result<Account> = runCatchingTransaction {
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

    override suspend fun deleteAccount(id: Int): Result<Unit> = runCatchingTransaction {
        AccountTable.deleteWhere {
            this@deleteWhere.id eq id
        }
    }
}