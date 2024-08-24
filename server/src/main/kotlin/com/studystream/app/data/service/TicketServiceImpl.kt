package com.studystream.app.data.service

import com.studystream.app.data.database.dao.TicketDao
import com.studystream.app.data.database.tables.TicketTable
import com.studystream.app.data.database.tables.TicketTypeTable
import com.studystream.app.data.database.utils.exists
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Ticket
import com.studystream.app.domain.service.TicketService
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andIfNotNull

class TicketServiceImpl(
    private val ticketDao: TicketDao,
    private val ticketTypeDao: TicketDao.TypeDao,
) : TicketService {
    override suspend fun createTicketType(
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?,
        creator: Account
    ): Result<Ticket.Type> = runCatchingTransaction {
        ticketTypeDao.new {
            this.title = title
            this.description = description
            this.totalEvents = totalEvents
            this.durationDays = durationDays
            this.creator = creator
        }
    }

    override suspend fun getTicketType(id: Id): Ticket.Type? = runSuspendedTransaction {
        ticketTypeDao.findById(id)
    }

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

    override suspend fun getTicketsTypes(): List<Ticket.Type> = runSuspendedTransaction {
        ticketTypeDao.all().toList()
    }

    override suspend fun existsTicketType(typeId: Id): Boolean = runSuspendedTransaction {
        ticketTypeDao.exists(TicketTypeTable.id eq typeId)
    }

    override suspend fun updateTicketType(
        id: Id,
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?
    ): Result<Ticket.Type> = runCatchingTransaction {
        ticketTypeDao.findByIdAndUpdate(id) {
            it.title = title
            it.description = description
            it.totalEvents = totalEvents
            it.durationDays = durationDays
        }!!
    }

    override suspend fun deleteTicketType(id: Id): Result<Unit> = runCatchingTransaction {
        ticketTypeDao.findById(id)!!.delete()
    }
}