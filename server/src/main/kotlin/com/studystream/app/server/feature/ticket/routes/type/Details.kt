package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
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
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getTicketTypeDetails(
    route: Tickets.Types.TypeId,
    ticketService: TicketService,
): TicketTypeDto = runSuspendedTransaction {
    ticketService
        .getTicketType(
            typeId = route.id,
        )
        .getOrThrow()
        .toDto()
}