package com.studystream.feature.auth.routes

import com.studystream.domain.exception.InvalidInputException
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.properties.AppProperties
import com.studystream.domain.properties.FeatureProperties
import com.studystream.domain.service.TokenService
import com.studystream.feature.auth.AuthRoute
import com.studystream.shared.payload.response.AuthResponse
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.get

private const val TOKEN_PREFIX = "Bearer "

internal fun Routing.installRefreshToken() {
    post<AuthRoute.RefreshTokenRoute> {
        val token = call.request.headers["Authorization"]
            ?: throw OperationRejectedException("Unauthorized")

        if (!token.startsWith(TOKEN_PREFIX)) {
            throw InvalidInputException("Token must starts with \"$TOKEN_PREFIX\"")
        }


        val result = refreshTokenRoute(
            properties = call.get<AppProperties>().featureProperties.refreshToken,
            token = token.substring(TOKEN_PREFIX.length),
            tokenService = call.get(),
        )

        call.respond(result)
    }
}

suspend fun refreshTokenRoute(
    properties: FeatureProperties.RefreshToken,
    token: String,
    tokenService: TokenService,
): AuthResponse = tokenService
    .refreshToken(token, properties.tokenRefreshThresholdMillis)
    .getOrThrow()
    .let {
        AuthResponse(
            id = it.account.id,
            token = it.token,
        )
    }
