package com.studystream.app.server.security

import com.studystream.app.domain.exception.OperationRejectedException
import com.studystream.app.domain.exception.ServiceException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.AccountPrincipal
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.security.Permission
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun ApplicationCall.requireAccount(): Account = principal<AccountPrincipal>()?.account
    ?: throw ServiceException("Unauthorized", HttpStatusCode.Unauthorized.value)

fun Account.hasPermission(permission: Permission): Boolean =
    roles.any { role ->
        role.privileges.any {
            it.permission == permission
        }
    }

fun Account.requirePermission(permission: Permission) {
    if (!hasPermission(permission)) {
        throw OperationRejectedException("Not enough rights")
    }
}

fun Account.choiceIdByPermission(
    permission: Permission,
    requestedId: Id?
): Id {
    if (requestedId == null) return idValue

    return when (hasPermission(permission)) {
        true -> requestedId
        false -> idValue
    }
}
