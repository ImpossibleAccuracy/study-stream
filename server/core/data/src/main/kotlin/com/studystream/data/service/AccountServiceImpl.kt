package com.studystream.data.service

import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.tables.AccountTable
import com.studystream.data.database.utils.runSuspendedTransaction
import com.studystream.data.mapper.toDomain
import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService

class AccountServiceImpl : AccountService {
    override suspend fun findUser(username: String): Account? = runSuspendedTransaction {
        AccountDao.find { AccountTable.username eq username }
            .firstOrNull()
            ?.toDomain()
    }
}