package com.studystream.data.service

import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.tables.AccountTable
import com.studystream.data.mapper.toDomain
import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService
import org.jetbrains.exposed.sql.transactions.transaction

class AccountServiceImpl : AccountService {
    override suspend fun findUser(username: String): Account? = transaction {
        AccountDao.find { AccountTable.username eq username }
            .firstOrNull()
            ?.toDomain()
    }
}