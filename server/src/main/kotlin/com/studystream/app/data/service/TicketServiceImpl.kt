package com.studystream.app.data.service

import com.studystream.app.data.database.dao.TicketDao
import com.studystream.app.data.database.tables.TicketTable
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Ticket
import com.studystream.app.domain.service.TicketService
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.andIfNotNull

class TicketServiceImpl(
    private val ticketDao: TicketDao,
    /*private val ticketTypeDao: TicketDao.TypeDao,*/
) : TicketService {
    override suspend fun getTickets(filters: TicketService.Filters): List<Ticket> =
        runSuspendedTransaction {
            ticketDao
                .find {
                    Op.TRUE
                        .andIfNotNull(
                            filters.ownerId?.let {
                                TicketTable.creator eq it
                            }
                        )
                        .andIfNotNull(
                            filters.profileId?.let {
                                TicketTable.profile eq it
                            }
                        )
                        .andIfNotNull(
                            filters.typeId?.let {
                                TicketTable.type eq it
                            }
                        )
                }
                .toList()
        }
}