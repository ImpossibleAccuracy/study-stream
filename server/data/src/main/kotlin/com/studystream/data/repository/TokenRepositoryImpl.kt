package com.studystream.data.repository

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.model.Account
import com.studystream.domain.properties.TokenProperties
import com.studystream.domain.repository.AccountRepository
import com.studystream.domain.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import java.time.Instant
import java.util.*

class TokenRepositoryImpl(
    private val accountRepository: AccountRepository,
    private val tokenProperties: TokenProperties,
) : TokenRepository {
    override suspend fun refreshToken(
        token: String,
        refreshThresholdMillis: Long,
    ): Result<TokenRepository.RefreshedToken> = ioCatchingCall(Dispatchers.Default) {
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

        val account = accountRepository.getAccount(accountId).getOrThrow()

        val newToken = generate(account).getOrThrow()

        TokenRepository.RefreshedToken(
            account = account,
            token = newToken,
        )
    }

    override suspend fun generate(account: Account): Result<String> = ioCatchingCall {
        JWT.create()
            .withAudience(tokenProperties.audience)
            .withIssuer(tokenProperties.issuer)
            .withClaim(tokenProperties.claimName, account.idValue.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + tokenProperties.ttl))
            .sign(Algorithm.HMAC256(tokenProperties.secret))
    }
}