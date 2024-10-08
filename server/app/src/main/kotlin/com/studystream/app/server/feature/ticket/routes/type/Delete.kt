package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.TicketRepository
import com.studystream.app.server.feature.ticket.Tickets
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

internal fun Route.installDeleteTicketTypeRoute() {
    authenticate {
        typeSafeDelete<Tickets.Types.TypeId> { route ->
            deleteTicketType(
                route = route,
                account = call.requireAccount(),
                ticketRepository = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteTicketType(
    route: Tickets.Types.TypeId,
    account: Account,
    ticketRepository: TicketRepository,
) = endpoint {
    account.requirePermission(Permission.TICKET_TYPES_DELETE)

    ticketRepository
        .deleteTicketType(
            type = ticketRepository.getTicketType(route.id).getOrThrow()
        )
        .getOrThrow()
}