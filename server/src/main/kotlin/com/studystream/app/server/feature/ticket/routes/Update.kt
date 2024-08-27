package com.studystream.app.server.feature.ticket.routes

import com.studystream.app.domain.service.ProfileService
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.TicketDto
import com.studystream.shared.payload.request.UpdateTicketRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Route.installUpdateTicketRoute() {
    authenticate {
        typeSafeGet<Tickets.TicketId> { route ->
            val result = updateTicket(
                route = route,
                body = call.receive(),
                ticketService = call.get(),
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun updateTicket(
    route: Tickets.TicketId,
    body: UpdateTicketRequest,
    ticketService: TicketService,
    profileService: ProfileService,
): TicketDto = endpoint {
    // TODO: add permissions check
    val ticket = ticketService.getTicket(route.id).getOrThrow()

    ticketService
        .updateTicket(
            ticket = ticket,
            profile = profileService.getProfile(body.profileId).getOrThrow(),
            type = ticketService.getTicketType(body.typeId).getOrThrow(),
            isActivated = body.isActivated ?: (ticket.activatedAt != null),
        )
        .getOrThrow()
        .toDto()
}