package com.studystream.data.datasource.base

import com.studystream.domain.model.Id
import com.studystream.domain.model.Ticket

interface TicketDataSource : CrudDataSource<Ticket> {
    suspend fun search(
        ownerId: Id? = null,
        profileId: Id? = null,
        typeId: Id? = null,
    ): List<Ticket>
}