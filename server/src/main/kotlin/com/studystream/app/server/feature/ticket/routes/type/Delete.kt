package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
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
                ticketService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteTicketType(
    route: Tickets.Types.TypeId,
    ticketService: TicketService,
) = endpoint {
    route.verify(ticketService)

    if (!ticketService.existsTicketType(route.id)) {
        throw ResourceNotFoundException("Ticket not found")
    }

    // TODO: check permissions to delete ticket type
    ticketService.deleteTicketType(typeId = route.id).getOrThrow()
}