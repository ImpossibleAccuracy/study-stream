package com.studystream.data.repository

import com.studystream.data.datasource.base.AccountDataSource
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.exception.ResourceNotFoundException
import com.studystream.domain.model.Account
import com.studystream.domain.repository.AccountRepository
import com.studystream.domain.utils.require

class AccountRepositoryImpl(
    private val accountDataSource: AccountDataSource,
) : AccountRepository {
    override suspend fun createAccount(username: String, password: String): Result<Account> = ioCatchingCall {
        if (accountDataSource.existsByUsername(username)) {
            throw ResourceNotFoundException("Username already used")
        }

        accountDataSource.save {
            this.username = username
            this.password = password
        }
    }

    override suspend fun getAccount(id: Int): Result<Account> = ioCatchingCall {
        accountDataSource.findById(id).require()
    }

    override suspend fun getAccount(username: String): Result<Account> = ioCatchingCall {
        accountDataSource
            .findByUsername(username)
            .require()
    }

    override suspend fun getAccounts(): List<Account> = ioCall {
        accountDataSource.getAll()
    }

    override suspend fun updateAccount(id: Int, username: String): Result<Account> = ioCatchingCall {
        if (accountDataSource.existsByUsernameEqAndIdNotEq(username, id)) {
            throw ResourceNotFoundException("Username already used")
        }

        accountDataSource.save(id) {
            this.username = username
        }

        accountDataSource
            .findById(id)
            .require()
    }

    override suspend fun deleteAccount(id: Int): Result<Unit> = ioCatchingCall {
        accountDataSource.deleteById(id)
            .let { isDeleted ->
                if (!isDeleted) {
                    throw ResourceNotFoundException("Account is not deleted")
                }
            }
    }
}