package com.studystream.app.data.service

import com.studystream.app.data.database.dao.TicketDao
import com.studystream.app.data.database.tables.TicketTable
import com.studystream.app.data.database.tables.TicketTypeTable
import com.studystream.app.data.database.utils.exists
import com.studystream.app.data.database.utils.runCatchingTransaction
import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.model.Profile
import com.studystream.app.domain.model.Ticket
import com.studystream.app.domain.service.TicketService
import com.studystream.app.domain.utils.require
import com.studystream.app.utils.now
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andIfNotNull

class TicketServiceImpl(
    private val ticketDao: TicketDao,
    private val ticketTypeDao: TicketDao.TypeDao,
) : TicketService {
    override suspend fun createTicket(
        owner: Account,
        profile: Profile,
        type: Ticket.Type,
        isActivated: Boolean
    ): Result<Ticket> = runCatchingTransaction {
        ticketDao.new {
            this.owner = owner
            this.profile = profile
            this.type = type
            this.activatedAt = when (isActivated) {
                true -> LocalDateTime.now()
                false -> null
            }
        }
    }

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

    override suspend fun getTicket(ticketId: Id): Result<Ticket> = runCatchingTransaction {
        ticketDao.findById(ticketId).require()
    }

    override suspend fun getTicketType(typeId: Id): Result<Ticket.Type> = runCatchingTransaction {
        ticketTypeDao.findById(typeId).require()
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

    override suspend fun existsTicket(ticketId: Id): Boolean = runSuspendedTransaction {
        ticketDao.exists(TicketTable.id eq ticketId)
    }

    override suspend fun existsTicketType(typeId: Id): Boolean = runSuspendedTransaction {
        ticketTypeDao.exists(TicketTypeTable.id eq typeId)
    }

    override suspend fun updateTicketType(
        typeId: Id,
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?
    ): Result<Ticket.Type> = runCatchingTransaction {
        ticketTypeDao.findByIdAndUpdate(typeId) {
            it.title = title
            it.description = description
            it.totalEvents = totalEvents
            it.durationDays = durationDays
        }!!
    }

    override suspend fun deleteTicketType(typeId: Id): Result<Unit> = runCatchingTransaction {
        ticketTypeDao.findById(typeId)!!.delete()
    }
}