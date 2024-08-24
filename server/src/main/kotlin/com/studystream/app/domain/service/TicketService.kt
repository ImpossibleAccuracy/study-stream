package com.studystream.app.domain.service

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Ticket

interface TicketService {
    suspend fun createTicketType(
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?,
        creator: Account,
    ): Result<Ticket.Type>

    suspend fun getTicketType(id: Id): Ticket.Type?

    suspend fun getTickets(filters: Filters): List<Ticket>

    suspend fun getTicketsTypes(): List<Ticket.Type>

    suspend fun existsTicketType(typeId: Id): Boolean

    suspend fun updateTicketType(
        id: Id,
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?,
    ): Result<Ticket.Type>

    suspend fun deleteTicketType(id: Id): Result<Unit>

    data class Filters(
        val ownerId: Id? = null,
        val profileId: Id? = null,
        val typeId: Id? = null,
    )
}