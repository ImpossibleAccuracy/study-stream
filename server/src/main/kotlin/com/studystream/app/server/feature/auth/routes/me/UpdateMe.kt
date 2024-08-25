package com.studystream.app.server.feature.auth.routes.me

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import com.studystream.app.server.feature.auth.AuthRoute
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
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

internal fun Routing.installUpdateMeRoute() {
    authenticate {
        typeSafePut<AuthRoute.MeRoute> {
            val account = call.requireAccount()

            val result = updateMeRoute(
                account = account,
                data = call.receive(),
                accountService = call.get()
            )

            call.respond(result)
        }
    }
}

suspend fun updateMeRoute(
    account: Account,
    data: UpdateAccountRequest,
    accountService: AccountService,
): AccountDto = endpoint {
    accountService
        .updateAccount(
            id = account.idValue,
            username = data.username
        )
        .getOrThrow()
        .toDto()
}
