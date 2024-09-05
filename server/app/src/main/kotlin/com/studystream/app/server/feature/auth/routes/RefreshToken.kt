package com.studystream.app.server.feature.auth.routes

import com.studystream.domain.exception.InvalidInputException
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.properties.feature.AuthProperties
import com.studystream.domain.repository.TokenRepository
import com.studystream.app.server.feature.auth.AuthRoute
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.response.AuthResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

private const val TOKEN_PREFIX = "Bearer "

internal fun Routing.installRefreshToken() {
    typeSafePost<AuthRoute.RefreshTokenRoute> {
        val token = call.request.headers["Authorization"]
            ?: throw OperationRejectedException("Unauthorized")

        if (!token.startsWith(TOKEN_PREFIX)) {
            throw InvalidInputException("Token must starts with \"$TOKEN_PREFIX\"")
        }

        val result = refreshTokenRoute(
            token = token.substring(TOKEN_PREFIX.length),
            properties = call.get(),
            tokenRepository = call.get(),
        )

        call.respond(result)
    }
}

suspend fun refreshTokenRoute(
    token: String,
    properties: AuthProperties,
    tokenRepository: TokenRepository,
): AuthResponse = endpoint {
    tokenRepository
        .refreshToken(token, properties.tokenRefreshThresholdMillis)
        .getOrThrow()
        .let {
            AuthResponse(
                id = it.account.idValue,
                token = it.token,
            )
        }
}
