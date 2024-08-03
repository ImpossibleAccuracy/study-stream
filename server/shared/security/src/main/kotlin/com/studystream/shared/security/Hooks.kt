package com.studystream.shared.security

import com.studystream.domain.exception.ServiceException
import com.studystream.domain.model.Account
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun ApplicationCall.requireAccount(): Account = principal<AccountPrincipal>()?.account
    ?: throw ServiceException("Unauthorized", HttpStatusCode.Unauthorized.value)
