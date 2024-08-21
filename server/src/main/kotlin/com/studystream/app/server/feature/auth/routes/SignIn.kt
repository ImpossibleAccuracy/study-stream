package com.studystream.app.server.feature.auth.routes

import com.studystream.app.domain.service.AuthService
import com.studystream.app.domain.service.TokenService
import com.studystream.app.server.feature.auth.AuthRoute
import com.studystream.shared.payload.request.SignInRequest
import com.studystream.shared.payload.response.AuthResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.get

internal fun Routing.installSignInRoute() {
    post<AuthRoute.SignInRoute> {
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
): AuthResponse {
    val user = authService
        .signIn(body.username, body.password)
        .getOrThrow()

    return AuthResponse(
        id = user.id.value,
        token = tokenService.generate(user).getOrThrow(),
    )
}
