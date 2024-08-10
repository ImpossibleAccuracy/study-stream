package com.studystream.app.server.feature.auth.routes.me

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import com.studystream.app.server.feature.auth.AuthRoutes
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.shared.payload.dto.AccountDto
import com.studystream.shared.payload.request.UpdateAccountRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.get

internal fun Routing.installUpdateMeRoute() {
    authenticate {
        put<AuthRoutes.MeRoute> {
            val account = call.requireAccount()

            val result = updateMeRoute(
                account = account,
                data = call.receive(),
                accountService = call.get<AccountService>()
            )

            call.respond(result)
        }
    }
}

suspend fun updateMeRoute(
    account: Account,
    data: UpdateAccountRequest,
    accountService: AccountService,
): AccountDto = accountService
    .updateUser(
        id = account.id.value,
        username = data.username
    )
    .getOrThrow()
    .toDto()
