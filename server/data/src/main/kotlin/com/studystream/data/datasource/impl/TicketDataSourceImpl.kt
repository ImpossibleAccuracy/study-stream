package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.TicketDao
import com.studystream.data.database.tables.TicketTable
import com.studystream.data.datasource.base.TicketDataSource
import com.studystream.data.model.TicketImpl
import com.studystream.domain.model.Id
import com.studystream.domain.model.Ticket
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.andIfNotNull

class TicketDataSourceImpl : TicketDataSource, CrudDataSourceImpl<Ticket, TicketImpl>(TicketDao) {
    override suspend fun search(ownerId: Id?, profileId: Id?, typeId: Id?): List<Ticket> =
        TicketDao
            .find {
                Op.TRUE
                    .andIfNotNull(
                        ownerId?.let {
                            TicketTable.creator eq it
                        }
                    )
                    .andIfNotNull(
                        profileId?.let {
                            TicketTable.profile eq it
                        }
                    )
                    .andIfNotNull(
                        typeId?.let {
                            TicketTable.type eq it
                        }
                    )
            }
            .toList()
}