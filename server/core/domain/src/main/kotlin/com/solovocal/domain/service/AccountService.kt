package com.solovocal.domain.service

import com.solovocal.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val accounts: Flow<List<Account>>

    suspend fun updateAccount(account: Account)
}