package com.studystream.app.server.feature.ticket.routes

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.dto.TicketDto
import com.studystream.shared.payload.request.CreateTicketRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Route.installCreateTicketRoute() {
    authenticate {
        typeSafePost<Tickets.Types> {
            val result = createTicket(
                body = call.receive(),
                account = call.requireAccount(),
                ticketService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createTicket(
    body: CreateTicketRequest,
    account: Account,
    ticketService: TicketService,
): TicketDto = runSuspendedTransaction {
    // TODO: check permissions to create ticket
    ticketService
        .createTicket(
            ownerId = body.creatorId ?: account.idValue,
            profileId = body.profileId,
            typeId = body.typeId,
            isActivated = body.isActivated,
        )
        .getOrThrow()
        .toDto()
}