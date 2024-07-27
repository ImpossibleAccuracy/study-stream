package com.solovocal.data.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.solovocal.domain.model.Account
import com.solovocal.domain.properties.TokenProperties
import com.solovocal.domain.service.TokenService
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