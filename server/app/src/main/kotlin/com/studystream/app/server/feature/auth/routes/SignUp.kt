package com.studystream.app.server.feature.auth.routes

import com.studystream.domain.repository.AuthRepository
import com.studystream.domain.repository.TokenRepository
import com.studystream.app.server.feature.auth.AuthRoute
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.request.SignUpRequest
import com.studystream.shared.payload.response.AuthResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installSignUpRoute() {
    typeSafePost<AuthRoute.SignUpRoute> {
        val result = signUpRoute(
            body = call.receive(),
            authRepository = call.get(),
            tokenRepository = call.get(),
        )

        call.respond(result)
    }
}

suspend fun signUpRoute(
    body: SignUpRequest,
    authRepository: AuthRepository,
    tokenRepository: TokenRepository,
): AuthResponse = endpoint {
    val user = authRepository
        .signUp(body.username, body.password)
        .getOrThrow()

    AuthResponse(
        id = user.idValue,
        token = tokenRepository.generate(user).getOrThrow(),
    )
}
