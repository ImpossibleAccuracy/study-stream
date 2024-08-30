package com.studystream.app.server.feature.ticket.routes

import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import com.studystream.domain.security.Permission
import com.studystream.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.hasPermission
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.TicketDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetTicketsListRoute() {
    authenticate {
        typeSafeGet<Tickets.List> { route ->
            val result = getTicketsList(
                ownerId = route.ownerId,
                profileId = route.profileId,
                typeId = route.typeId,
                account = call.requireAccount(),
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getTicketsList(
    ownerId: Id?,
    profileId: Id?,
    typeId: Id?,
    account: Account,
    ticketService: TicketService,
): List<TicketDto> = endpoint {
    ticketService
        .getTickets(
            filters = TicketService.Filters(
                ownerId = when {
                    ownerId == null || account.hasPermission(Permission.TICKETS_READ) -> ownerId
                    else -> account.idValue
                },
                profileId = profileId,
                typeId = typeId,
            )
        )
        .map {
            it.toDto()
        }
}
