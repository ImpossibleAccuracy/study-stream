package com.studystream.app.server.feature.ticket.routes

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
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
): List<TicketDto> = runSuspendedTransaction {
    // TODO: add permissions check
    // If account is admin -> can get all tickets
    // Else ownerId is replaced by account.id
    ticketService
        .getTickets(
            filters = TicketService.Filters(
                ownerId = ownerId ?: account.idValue,
                profileId = profileId,
                typeId = typeId,
            )
        )
        .map {
            it.toDto()
        }
}
