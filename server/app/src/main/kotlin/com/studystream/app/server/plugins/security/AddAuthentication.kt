package com.studystream.app.server.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.studystream.app.model.AccountPrincipal
import com.studystream.domain.properties.TokenProperties
import com.studystream.domain.repository.AccountRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.get

internal fun Application.addAuthentication() {
    val tokenProperties = get<TokenProperties>()
    val accountRepository = get<AccountRepository>()

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
                    accountRepository.getAccount(id).getOrNull()
                } ?: return@validate null

                AccountPrincipal(user)
            }
        }
    }
}