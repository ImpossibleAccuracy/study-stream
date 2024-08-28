package com.studystream.app.server.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.studystream.app.domain.model.AccountPrincipal
import com.studystream.app.domain.properties.TokenProperties
import com.studystream.app.domain.service.AccountService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.get

internal fun Application.addAuthentication() {
    val tokenProperties = get<TokenProperties>()
    val accountService = get<AccountService>()

    authentication {
        jwt {
            realm = tokenProperties.realm

            verifier(
                JWT.require(Algorithm.HMAC256(tokenProperties.secret))
                    .withAudience(tokenProperties.audience)
                    .withIssuer(tokenProperties.issuer)
                    .build()
            )

            validate { credential ->
                if (tokenProperties.audience !in credential.payload.audience) return@validate null

                val id = credential[tokenProperties.claimName]
                    ?.toInt()
                    ?: return@validate null

                val user = newSuspendedTransaction {
                    accountService.getAccount(id).getOrNull()
                } ?: return@validate null

                AccountPrincipal(user)
            }
        }
    }
}