package com.studystream.app.server.feature.ticket.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.AccountRepository
import com.studystream.domain.repository.ProfileRepository
import com.studystream.domain.repository.TicketRepository
import com.studystream.app.server.feature.ticket.Tickets
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.choiceIdByPermission
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.endpoint
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
        typeSafePost<Tickets> {
            val result = createTicket(
                body = call.receive(),
                account = call.requireAccount(),
                ticketRepository = call.get(),
                accountRepository = call.get(),
                profileRepository = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createTicket(
    body: CreateTicketRequest,
    account: Account,
    ticketRepository: TicketRepository,
    accountRepository: AccountRepository,
    profileRepository: ProfileRepository,
): TicketDto = endpoint {
    val creator = accountRepository
        .getAccount(id = account.choiceIdByPermission(Permission.TICKETS_CREATE, body.ownerId))
        .getOrThrow()

    val profile = profileRepository.getProfile(body.profileId).getOrThrow()
    val type = ticketRepository.getTicketType(body.typeId).getOrThrow()

    ticketRepository
        .createTicket(
            owner = creator,
            profile = profile,
            type = type,
            isActivated = body.isActivated,
        )
        .getOrThrow()
        .toDto()
}