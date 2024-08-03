package com.studystream.feature.auth.routes

import com.studystream.domain.service.AuthService
import com.studystream.domain.service.TokenService
import com.studystream.feature.auth.AuthRoute
import com.studystream.shared.payload.response.AuthResponse
import com.studystream.shared.payload.request.SignUpRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.get

internal fun Routing.installSignUpRoute() {
    post<AuthRoute.SignUpRoute> {
        val result = signUpRoute(
            body = call.receive(),
            authService = call.get<AuthService>(),
            tokenService = call.get<TokenService>(),
        )

        call.respond(result)
    }
}

suspend fun signUpRoute(
    body: SignUpRequest,
    authService: AuthService,
    tokenService: TokenService,
): AuthResponse {
    val user = authService
        .signUp(body.username, body.password)
        .getOrThrow()

    return AuthResponse(
        id = user.id,
        token = tokenService.generate(user),
    )
}
