package com.solovocal.plugin.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.solovocal.domain.properties.TokenProperties
import com.solovocal.domain.service.AuthService
import com.solovocal.shared.security.AccountPrincipal
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

fun Application.configureSecurity(
    tokenProperties: TokenProperties,
    authService: AuthService,
) {
    authentication {
        jwt {
            realm = tokenProperties.realm

            verifier(
                JWT
                    .require(Algorithm.HMAC256(tokenProperties.secret))
                    .withAudience(tokenProperties.audience)
                    .withIssuer(tokenProperties.issuer)
                    .build()
            )

            validate { credential ->
                if (tokenProperties.audience !in credential.payload.audience) return@validate null

                val username = credential[tokenProperties.claimName] ?: return@validate null

                val user = authService.findUser(username) ?: return@validate null

                AccountPrincipal(user)
            }
        }
    }
}
