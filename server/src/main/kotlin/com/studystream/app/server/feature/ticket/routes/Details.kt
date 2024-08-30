package com.studystream.app.server.feature.ticket.routes

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.security.Permission
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
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
                account = call.requireAccount(),
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getTicketDetails(
    route: Tickets.TicketId,
    account: Account,
    ticketService: TicketService,
): TicketDto = endpoint {
    ticketService
        .getTicket(
            ticketId = route.id,
        )
        .getOrThrow()
        .also {
            if (it.owner.idValue != account.idValue) {
                account.requirePermission(Permission.TICKETS_READ)
            }
        }
        .toDto()
}