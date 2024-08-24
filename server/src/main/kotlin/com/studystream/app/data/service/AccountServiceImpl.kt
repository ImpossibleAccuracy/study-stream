package com.studystream.app.data.service

import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.data.database.tables.AccountTable
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class AccountServiceImpl(
    private val accountDao: AccountDao,
) : AccountService {
    override suspend fun findUser(id: Int): Account? = runSuspendedTransaction {
        accountDao.findById(id)
    }

    override suspend fun findUser(username: String): Account? = runSuspendedTransaction {
        accountDao
            .find { AccountTable.username eq username }
            .firstOrNull()
    }

    override suspend fun updateUser(id: Int, username: String): Result<Account> = runCatchingTransaction {
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

    override suspend fun deleteUser(id: Int): Result<Unit> = runCatchingTransaction {
        AccountTable.deleteWhere {
            this@deleteWhere.id eq id
        }
    }
}