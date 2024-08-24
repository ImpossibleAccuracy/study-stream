package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.typeSafePut
import com.studystream.shared.payload.dto.TicketTypeDto
import com.studystream.shared.payload.request.UpsertTicketTypeRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Route.installUpdateTicketTypeRoute() {
    authenticate {
        typeSafePut<Tickets.Types.TypeId> { route ->
            val result = updateTicketType(
                route = route,
                body = call.receive<UpsertTicketTypeRequest>(),
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun updateTicketType(
    route: Tickets.Types.TypeId,
    body: UpsertTicketTypeRequest,
    ticketService: TicketService,
): TicketTypeDto = runSuspendedTransaction {
    route.verify(ticketService)

    // TODO: check permissions to update ticket type
    ticketService
        .updateTicketType(
            id = route.id,
            title = body.title,
            description = body.description,
            totalEvents = body.totalEvents,
            durationDays = body.durationDays,
        )
        .getOrThrow()
        .toDto()
}