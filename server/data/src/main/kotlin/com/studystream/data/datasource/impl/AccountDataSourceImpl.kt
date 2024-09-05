package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.tables.AccountTable
import com.studystream.data.database.utils.exists
import com.studystream.data.database.utils.findSingle
import com.studystream.data.datasource.base.AccountDataSource
import com.studystream.data.model.AccountImpl
import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and

class AccountDataSourceImpl : AccountDataSource, CrudDataSourceImpl<Account, AccountImpl>(AccountDao) {
    override suspend fun existsByUsername(username: String): Boolean =
        AccountDao.exists(AccountTable.username eq username)

    override suspend fun existsByUsernameEqAndIdNotEq(username: String, id: Id): Boolean =
        AccountDao.exists(
            (AccountTable.username eq username) and
                    (AccountTable.id neq id)
        )

    override suspend fun findByUsername(username: String): Account? =
        AccountDao.findSingle(AccountTable.username eq username)
}