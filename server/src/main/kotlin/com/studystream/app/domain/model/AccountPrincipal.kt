package com.studystream.app.domain.model

import io.ktor.server.auth.*

data class AccountPrincipal(
    val account: Account,
) : Principal