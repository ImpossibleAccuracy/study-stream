package com.studystream.app.server.feature.account.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.AccountRepository
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeDelete
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installDeleteAccountsRoute() {
    authenticate {
        typeSafeDelete<Accounts.AccountId> { route ->
            deleteAccount(
                route = route,
                account = call.requireAccount(),
                accountRepository = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteAccount(
    route: Accounts.AccountId,
    account: Account,
    accountRepository: AccountRepository,
) = endpoint {
    account.requirePermission(Permission.ACCOUNTS_DELETE)

    accountRepository
        .deleteAccount(route.id)
        .getOrThrow()
}
