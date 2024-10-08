package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.domain.repository.TicketRepository
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.TicketTypeDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Route.installGetTicketTypeDetailsRoute() {
    authenticate {
        typeSafeGet<Tickets.Types.TypeId> { route ->
            val result = getTicketTypeDetails(
                route = route,
                ticketRepository = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getTicketTypeDetails(
    route: Tickets.Types.TypeId,
    ticketRepository: TicketRepository,
): TicketTypeDto = endpoint {
    ticketRepository
        .getTicketType(
            typeId = route.id,
        )
        .getOrThrow()
        .toDto()
}