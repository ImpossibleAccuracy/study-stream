package com.studystream.app.server.security

import com.studystream.app.model.AccountPrincipal
import com.studystream.domain.exception.OperationRejectedException
import com.studystream.domain.exception.ServiceException
import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import com.studystream.domain.security.Permission
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun ApplicationCall.requireAccount(): Account = principal<AccountPrincipal>()?.account
    ?: throw ServiceException("Unauthorized", HttpStatusCode.Unauthorized.value)

fun Account.hasPermission(permission: Permission): Boolean =
    roles.any { role ->
        role.permissions.any {
            it == permission
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
