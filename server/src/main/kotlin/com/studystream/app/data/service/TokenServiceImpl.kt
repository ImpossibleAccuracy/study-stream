package com.studystream.app.data.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.studystream.app.data.utils.ioCall
import com.studystream.app.domain.exception.OperationRejectedException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.properties.TokenProperties
import com.studystream.app.domain.service.AccountService
import com.studystream.app.domain.service.TokenService
import java.time.Instant
import java.util.*

class TokenServiceImpl(
    private val accountService: AccountService,
    private val tokenProperties: TokenProperties,
) : TokenService {
    override suspend fun refreshToken(
        token: String,
        refreshThresholdMillis: Long,
    ): Result<TokenService.RefreshedToken> = runCatching {
        val decoded = JWT.decode(token)

        // TODO: add tests to business logic
        decoded.expiresAtAsInstant.isAfter(
            Instant.now().plusMillis(refreshThresholdMillis)
        ).let { isNotRefreshable ->
            if (isNotRefreshable) {
                throw OperationRejectedException("Token cannot be refreshed")
            }
        }

        val accountId = decoded.claims[tokenProperties.claimName]!!
            .asString()
            .toIntOrNull() ?: throw OperationRejectedException("Invalid token content")

        val account = accountService.getAccount(accountId).getOrThrow()

        val newToken = generate(account).getOrThrow()

        TokenService.RefreshedToken(
            account = account,
            token = newToken,
        )
    }

    override suspend fun generate(account: Account): Result<String> = ioCall {
        JWT.create()
            .withAudience(tokenProperties.audience)
            .withIssuer(tokenProperties.issuer)
            .withClaim(tokenProperties.claimName, account.idValue.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + tokenProperties.ttl))
            .sign(Algorithm.HMAC256(tokenProperties.secret))
    }
}