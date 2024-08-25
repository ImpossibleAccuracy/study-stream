package com.studystream.app.domain.service

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Profile
import com.studystream.app.domain.model.Ticket

interface TicketService {
    suspend fun createTicket(
        owner: Account,
        profile: Profile,
        type: Ticket.Type,
        isActivated: Boolean
    ): Result<Ticket>

    suspend fun createTicketType(
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?,
        creator: Account,
    ): Result<Ticket.Type>

    suspend fun getTicket(ticketId: Id): Result<Ticket>

    suspend fun getTicketType(typeId: Id): Result<Ticket.Type>

    suspend fun getTickets(filters: Filters): List<Ticket>

    suspend fun getTicketsTypes(): List<Ticket.Type>

    suspend fun existsTicket(ticketId: Id): Boolean

    suspend fun existsTicketType(typeId: Id): Boolean

    suspend fun updateTicketType(
        typeId: Id,
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?,
    ): Result<Ticket.Type>

    suspend fun deleteTicketType(typeId: Id): Result<Unit>

    data class Filters(
        val ownerId: Id? = null,
        val profileId: Id? = null,
        val typeId: Id? = null,
    )
}