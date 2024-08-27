package com.studystream.app.server.feature.account.routes

import com.studystream.app.domain.service.AuthService
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.dto.AccountDto
import com.studystream.shared.payload.request.CreateAccountRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installCreateAccountRoute() {
    authenticate {
        typeSafePost<Accounts> {
            val result = createAccount(
                body = call.receive(),
                authService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createAccount(
    body: CreateAccountRequest,
    authService: AuthService,
): AccountDto = endpoint {
    // TODO: add admin check
    authService
        .signUp(body.username, body.password)
        .getOrThrow()
        .toDto()
}
