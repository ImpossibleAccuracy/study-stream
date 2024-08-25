package com.studystream.app.server.feature.ticket.routes

import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
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
                ticketService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteTicket(
    route: Tickets.TicketId,
    ticketService: TicketService,
) = endpoint {
    // TODO: add permissions check
    ticketService
        .deleteTicket(ticket = ticketService.getTicket(route.id).getOrThrow())
        .getOrThrow()
}