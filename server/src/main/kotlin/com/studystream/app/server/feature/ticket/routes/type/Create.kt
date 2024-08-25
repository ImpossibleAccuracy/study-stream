package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.dto.TicketTypeDto
import com.studystream.shared.payload.request.UpsertTicketTypeRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Route.installCreateTicketTypeRoute() {
    authenticate {
        typeSafePost<Tickets.Types> {
            val result = createTicketType(
                body = call.receive(),
                account = call.requireAccount(),
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createTicketType(
    body: UpsertTicketTypeRequest,
    account: Account,
    ticketService: TicketService,
): TicketTypeDto = endpoint {
    // TODO: check permissions to create ticket type
    ticketService
        .createTicketType(
            title = body.title,
            description = body.description,
            totalEvents = body.totalEvents,
            durationDays = body.durationDays,
            creator = account,
        )
        .getOrThrow()
        .toDto()
}