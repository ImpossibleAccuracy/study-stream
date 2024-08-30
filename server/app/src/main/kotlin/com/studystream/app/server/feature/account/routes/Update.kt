package com.studystream.app.server.feature.account.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.service.AccountService
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.LazyBody
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafePut
import com.studystream.shared.payload.dto.AccountDto
import com.studystream.shared.payload.request.UpdateAccountRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installUpdateAccountsRoute() {
    authenticate {
        typeSafePut<Accounts.AccountId> { route ->
            val result = updateAccount(
                route = route,
                account = call.requireAccount(),
                body = LazyBody { call.receive() },
                accountService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun updateAccount(
    route: Accounts.AccountId,
    account: Account,
    body: LazyBody<UpdateAccountRequest>,
    accountService: AccountService,
): AccountDto = endpoint {
    account.requirePermission(Permission.ACCOUNTS_UPDATE)

    accountService
        .updateAccount(route.id, body().username)
        .getOrThrow()
        .toDto()
}
