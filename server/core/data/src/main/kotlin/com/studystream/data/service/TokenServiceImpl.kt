package com.studystream.data.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.exception.ResourceNotFoundException
import com.studystream.domain.model.Account
import com.studystream.domain.properties.TokenProperties
import com.studystream.domain.service.AccountService
import com.studystream.domain.service.TokenService
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

        val username = decoded.claims[tokenProperties.claimName]!!.asString()

        val account = accountService.findUser(username)
            ?: throw ResourceNotFoundException("User not found")

        val newToken = generate(account)

        TokenService.RefreshedToken(
            account = account,
            token = newToken,
        )
    }

    override suspend fun generate(account: Account): String = JWT.create()
        .withAudience(tokenProperties.audience)
        .withIssuer(tokenProperties.issuer)
        .withClaim(tokenProperties.claimName, account.username)
        .withExpiresAt(Date(System.currentTimeMillis() + tokenProperties.ttl))
        .sign(Algorithm.HMAC256(tokenProperties.secret))
}