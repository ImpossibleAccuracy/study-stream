package com.solovocal.data.service

import com.solovocal.data.database.dao.UserDao
import com.solovocal.domain.model.Account
import com.solovocal.domain.service.AccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.transaction

class AccountServiceImpl : AccountService {
    private val _accounts = MutableStateFlow<List<Account>>(listOf())
    override val accounts: Flow<List<Account>> = _accounts.filterNotNull()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            transaction {
                _accounts.value = UserDao.all().map {
                    Account(
                        email = it.name
                    )
                }
            }
        }
    }

    override suspend fun updateAccount(account: Account) {
        _accounts.value = _accounts.value.plus(account)
    }
}