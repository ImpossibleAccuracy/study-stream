package com.solovocal.shared.security

import com.solovocal.domain.model.Account
import io.ktor.server.auth.*

data class AccountPrincipal(
    val account: Account,
) : Principal