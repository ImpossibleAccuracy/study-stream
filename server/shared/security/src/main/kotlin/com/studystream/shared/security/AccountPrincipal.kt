package com.studystream.shared.security

import com.studystream.domain.model.Account
import io.ktor.server.auth.*

data class AccountPrincipal(
    val account: Account,
) : Principal