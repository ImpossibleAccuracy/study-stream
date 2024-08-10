package com.studystream.app.server.feature.auth.routes

import com.studystream.app.domain.exception.InvalidInputException
import com.studystream.app.domain.exception.OperationRejectedException
import com.studystream.app.domain.properties.feature.AuthProperties
import com.studystream.app.domain.service.TokenService
import com.studystream.app.server.feature.auth.AuthRoutes
import com.studystream.shared.payload.response.AuthResponse
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.get

private const val TOKEN_PREFIX = "Bearer "

internal fun Routing.installRefreshToken() {
    post<AuthRoutes.RefreshTokenRoute> {
        val token = call.request.headers["Authorization"]
            ?: throw OperationRejectedException("Unauthorized")

        if (!token.startsWith(TOKEN_PREFIX)) {
            throw InvalidInputException("Token must starts with \"$TOKEN_PREFIX\"")
        }

        val result = refreshTokenRoute(
            token = token.substring(TOKEN_PREFIX.length),
            properties = call.get(),
            tokenService = call.get(),
        )

        call.respond(result)
    }
}

suspend fun refreshTokenRoute(
    token: String,
    properties: AuthProperties,
    tokenService: TokenService,
): AuthResponse = tokenService
    .refreshToken(token, properties.tokenRefreshThresholdMillis)
    .getOrThrow()
    .let {
        AuthResponse(
            id = it.account.id.value,
            token = it.token,
        )
    }
