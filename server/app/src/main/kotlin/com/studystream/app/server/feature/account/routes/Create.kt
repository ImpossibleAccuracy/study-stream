package com.studystream.app.server.feature.account.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.AuthRepository
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.LazyBody
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
                body = LazyBody { call.receive() },
                account = call.requireAccount(),
                authRepository = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createAccount(
    body: LazyBody<CreateAccountRequest>,
    account: Account,
    authRepository: AuthRepository,
): AccountDto = endpoint {
    account.requirePermission(Permission.ACCOUNTS_CREATE)

    authRepository
        .signUp(body().username, body().password)
        .getOrThrow()
        .toDto()
}
