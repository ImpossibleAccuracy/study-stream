package com.studystream.app.server.feature.ticket.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Route.installDeleteTicketRoute() {
    authenticate {
        typeSafeGet<Tickets.TicketId> { route ->
            deleteTicket(
                route = route,
                account = call.requireAccount(),
                ticketService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteTicket(
    route: Tickets.TicketId,
    account: Account,
    ticketService: TicketService,
) = endpoint {
    val ticket = ticketService.getTicket(route.id).getOrThrow()

    if (ticket.owner.idValue != account.idValue) {
        account.requirePermission(Permission.TICKETS_DELETE)
    }

    ticketService
        .deleteTicket(ticket = ticket)
        .getOrThrow()
}