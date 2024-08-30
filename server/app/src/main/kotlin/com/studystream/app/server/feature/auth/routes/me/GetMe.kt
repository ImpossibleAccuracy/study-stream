package com.studystream.app.server.feature.auth.routes.me

import com.studystream.domain.model.Account
import com.studystream.app.server.feature.auth.AuthRoute
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Routing.installGetMeRoute() {
    authenticate {
        typeSafeGet<AuthRoute.MeRoute> {
            val account = call.requireAccount()

            val result = getMeRoute(account)

            call.respond(result)
        }
    }
}

suspend inline fun getMeRoute(account: Account) = endpoint { account.toDto() }
