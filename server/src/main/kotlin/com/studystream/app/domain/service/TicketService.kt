package com.studystream.app.domain.service

import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Ticket

interface TicketService {
    suspend fun getTickets(filters: Filters): List<Ticket>

    data class Filters(
        val ownerId: Id? = null,
        val profileId: Id? = null,
        val typeId: Id? = null,
    )
}