package com.studystream.feature.auth.routes.me

import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService
import com.studystream.feature.auth.AuthRoute
import com.studystream.shared.security.requireAccount
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installDeleteMeRoute() {
    authenticate {
        delete<AuthRoute.MeRoute> {
            val account = call.requireAccount()

            deleteMeRoute(
                account = account,
                accountService = call.get<AccountService>()
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteMeRoute(
    account: Account,
    accountService: AccountService,
) = accountService
    .deleteUser(id = account.id)
    .getOrThrow()
