package com.studystream.app.server.feature.auth.routes

import com.studystream.domain.service.AuthService
import com.studystream.domain.service.TokenService
import com.studystream.app.server.feature.auth.AuthRoute
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.request.SignInRequest
import com.studystream.shared.payload.response.AuthResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installSignInRoute() {
    typeSafePost<AuthRoute.SignInRoute> {
        val result = signInRoute(
            body = call.receive(),
            authService = call.get(),
            tokenService = call.get(),
        )

        call.respond(result)
    }
}

suspend fun signInRoute(
    body: SignInRequest,
    authService: AuthService,
    tokenService: TokenService,
): AuthResponse = endpoint {
    val user = authService
        .signIn(body.username, body.password)
        .getOrThrow()

    AuthResponse(
        id = user.idValue,
        token = tokenService.generate(user).getOrThrow(),
    )
}
