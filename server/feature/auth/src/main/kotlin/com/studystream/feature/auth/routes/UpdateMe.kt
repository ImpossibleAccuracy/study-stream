package com.studystream.feature.auth.routes

import com.studystream.domain.model.Account
import com.studystream.domain.service.AccountService
import com.studystream.feature.auth.AuthRoute
import com.studystream.shared.feature.mapper.toDto
import com.studystream.shared.payload.dto.AccountDto
import com.studystream.shared.payload.request.UpdateAccountRequest
import com.studystream.shared.security.requireAccount
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.get

internal fun Routing.installUpdateMeRoute() {
    authenticate {
        put<AuthRoute.MeRoute> {
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
        id = account.id,
        username = data.username
    )
    .getOrThrow()
    .toDto()
