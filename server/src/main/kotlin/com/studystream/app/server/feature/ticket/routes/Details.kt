package com.studystream.app.server.feature.ticket.routes

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.TicketDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Route.installGetTicketDetailsRoute() {
    authenticate {
        typeSafeGet<Tickets.TicketId> { route ->
            val result = getTicketDetails(
                route = route,
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getTicketDetails(
    route: Tickets.TicketId,
    ticketService: TicketService,
): TicketDto = runSuspendedTransaction {
    // TODO: add permissions check
    ticketService
        .getTicket(
            ticketId = route.id,
        )
        ?.toDto()
        ?: throw ResourceNotFoundException("Ticket not found")
}