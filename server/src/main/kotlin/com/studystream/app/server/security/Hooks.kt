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

fun Account.requirePermission(permission: Permission) =
    roles
        .none { role ->
            role.privileges.any {
                it.permission == permission
            }
        }
        .let { noPermission ->
            if (noPermission) {
                throw OperationRejectedException("Not enough rights")
            }
        }

fun choiceIdByPermission(
    account: Account,
    permission: Permission,
    ownId: Id,
    requestedId: Id?
): Id {
    if (requestedId == null) return ownId

    return account.roles
        .any { role ->
            role.privileges.any {
                it.permission == permission
            }
        }
        .let {
            when (it) {
                true -> ownId
                false -> requestedId
            }
        }
}
