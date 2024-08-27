package com.studystream.app.server.feature.account.routes

import com.studystream.app.domain.service.AccountService
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installDeleteAccountsRoute() {
    authenticate {
        typeSafeGet<Accounts.AccountId> { route ->
            deleteAccount(
                route = route,
                accountService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteAccount(
    route: Accounts.AccountId,
    accountService: AccountService,
) = endpoint {
    // TODO: add admin check
    accountService
        .deleteAccount(route.id)
        .getOrThrow()
}
