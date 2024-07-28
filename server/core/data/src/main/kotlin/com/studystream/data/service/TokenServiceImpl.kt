package com.studystream.data.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.studystream.domain.model.Account
import com.studystream.domain.properties.TokenProperties
import com.studystream.domain.service.TokenService
import java.util.*

class TokenServiceImpl(
    private val tokenProperties: TokenProperties,
) : TokenService {
    override suspend fun generate(account: Account): String = JWT.create()
        .withAudience(tokenProperties.audience)
        .withIssuer(tokenProperties.issuer)
        .withClaim(tokenProperties.claimName, account.email)
        .withExpiresAt(Date(System.currentTimeMillis() + tokenProperties.ttl))
        .sign(Algorithm.HMAC256(tokenProperties.secret))
}