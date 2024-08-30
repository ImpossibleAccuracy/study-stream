package com.studystream.app.server.feature.ticket.routes.type

import com.studystream.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.TicketTypeDto
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetTicketsTypesListRoute() {
    typeSafeGet<Tickets.Types.List> {
        val result = getTicketsTypesList(
            ticketService = call.get(),
        )

        call.respond(result)
    }
}

suspend fun getTicketsTypesList(
    ticketService: TicketService,
): List<TicketTypeDto> = endpoint {
    ticketService
        .getTicketsTypes()
        .map {
            it.toDto()
        }
}
