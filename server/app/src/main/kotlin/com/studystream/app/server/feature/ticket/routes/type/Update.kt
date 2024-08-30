package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
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
                account = call.requireAccount(),
                body = call.receive<UpsertTicketTypeRequest>(),
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun updateTicketType(
    route: Tickets.Types.TypeId,
    account: Account,
    body: UpsertTicketTypeRequest,
    ticketService: TicketService,
): TicketTypeDto = endpoint {
    account.requirePermission(Permission.TICKET_TYPES_UPDATE)

    ticketService
        .updateTicketType(
            type = ticketService.getTicketType(route.id).getOrThrow(),
            title = body.title,
            description = body.description,
            totalEvents = body.totalEvents,
            durationDays = body.durationDays,
        )
        .getOrThrow()
        .toDto()
}