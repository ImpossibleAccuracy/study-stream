package com.studystream.data.service

import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.tables.AccountTable
import com.studystream.data.database.utils.runCatchingTransaction
import com.studystream.data.database.utils.runSuspendedTransaction
import com.studystream.data.mapper.toDomain
import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService
import org.jetbrains.exposed.sql.update

class AccountServiceImpl : AccountService {
    override suspend fun findUser(id: Int): Account? = runSuspendedTransaction {
        AccountDao.findById(id)?.toDomain()
    }

    override suspend fun findUser(username: String): Account? = runSuspendedTransaction {
        AccountDao.find { AccountTable.username eq username }
            .firstOrNull()
            ?.toDomain()
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

        AccountDao[id].toDomain()
    }
}