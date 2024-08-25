package com.studystream.app.server.feature.auth.routes.me

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import com.studystream.app.server.feature.auth.AuthRoute
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.typeSafeDelete
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installDeleteMeRoute() {
    authenticate {
        typeSafeDelete<AuthRoute.MeRoute> {
            deleteMeRoute(
                account = call.requireAccount(),
                accountService = call.get()
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteMeRoute(
    account: Account,
    accountService: AccountService,
) = accountService
    .deleteAccount(id = account.idValue)
    .getOrThrow()
