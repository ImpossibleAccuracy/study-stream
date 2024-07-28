package com.studystream.feature.auth.routes

import com.studystream.domain.service.AuthService
import com.studystream.domain.service.TokenService
import com.studystream.feature.auth.AuthRoute
import com.studystream.feature.auth.payload.AuthResponse
import com.studystream.feature.auth.payload.SignInRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.get

internal fun Routing.installSignInRoute() {
    post<AuthRoute.SignInRoute> {
        val body = call.receive<SignInRequest>()

        val result = signInRoute(
            body = body,
            authService = call.get<AuthService>(),
            tokenService = call.get<TokenService>(),
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
        .signIn(body.email, body.password)
        .getOrThrow()

    return AuthResponse(
        id = user.id,
        token = tokenService.generate(user),
    )
}
