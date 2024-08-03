package com.studystream.feature.auth.routes

import com.studystream.domain.model.Account
import com.studystream.feature.auth.AuthRoute
import com.studystream.shared.feature.mapper.toDto
import com.studystream.shared.security.requireAccount
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Routing.installGetMeRoute() {
    authenticate {
        get<AuthRoute.GetMeRoute> {
            val account = call.requireAccount()

            val result = getMeRoute(account)

            call.respond(result)
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun getMeRoute(account: Account) = account.toDto()
