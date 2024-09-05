package com.studystream.app.server.feature.account.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.AccountRepository
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.AccountDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetAccountsListRoute() {
    authenticate {
        typeSafeGet<Accounts.List> {
            val result = getAccountsList(
                account = call.requireAccount(),
                accountRepository = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getAccountsList(
    account: Account,
    accountRepository: AccountRepository,
): List<AccountDto> = endpoint {
    account.requirePermission(Permission.ACCOUNTS_READ)

    accountRepository
        .getAccounts()
        .map { it.toDto() }
}
