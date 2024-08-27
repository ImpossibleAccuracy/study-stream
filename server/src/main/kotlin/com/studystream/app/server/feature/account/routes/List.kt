package com.studystream.app.server.feature.account.routes

import com.studystream.app.domain.service.AccountService
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.mapper.toDto
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
                accountService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getAccountsList(
    accountService: AccountService,
): List<AccountDto> = endpoint {
    // TODO: add admin check
    accountService
        .getAccounts()
        .map { it.toDto() }
}
