package com.studystream.plugin.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.studystream.domain.properties.TokenProperties
import com.studystream.domain.service.AccountService
import com.studystream.shared.security.AccountPrincipal
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(
    tokenProperties: TokenProperties,
    accountService: AccountService,
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

                val id = credential[tokenProperties.claimName]
                    ?.toInt()
                    ?: return@validate null

                val user = accountService.findUser(id)
                    ?: return@validate null

                AccountPrincipal(user)
            }
        }
    }
}
