package com.studystream.app.server.security

import com.studystream.app.domain.exception.ServiceException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.AccountPrincipal
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun ApplicationCall.requireAccount(): Account = principal<AccountPrincipal>()?.account
    ?: throw ServiceException("Unauthorized", HttpStatusCode.Unauthorized.value)
