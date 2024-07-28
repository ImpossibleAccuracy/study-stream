package com.studystream.domain.service

import com.studystream.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val accounts: Flow<List<Account>>

    suspend fun updateAccount(account: Account)
}