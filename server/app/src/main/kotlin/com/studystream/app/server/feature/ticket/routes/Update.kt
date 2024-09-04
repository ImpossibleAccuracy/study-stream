package com.studystream.app.server.feature.ticket.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.ProfileRepository
import com.studystream.domain.repository.TicketRepository
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
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
                account = call.requireAccount(),
                body = call.receive(),
                ticketRepository = call.get(),
                profileRepository = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun updateTicket(
    route: Tickets.TicketId,
    account: Account,
    body: UpdateTicketRequest,
    ticketRepository: TicketRepository,
    profileRepository: ProfileRepository,
): TicketDto = endpoint {
    val ticket = ticketRepository.getTicket(route.id).getOrThrow()

    if (ticket.owner.idValue != account.idValue) {
        account.requirePermission(Permission.TICKETS_UPDATE)
    }

    ticketRepository
        .updateTicket(
            ticket = ticket,
            profile = profileRepository.getProfile(body.profileId).getOrThrow(),
            type = ticketRepository.getTicketType(body.typeId).getOrThrow(),
            isActivated = body.isActivated ?: (ticket.activatedAt != null),
        )
        .getOrThrow()
        .toDto()
}