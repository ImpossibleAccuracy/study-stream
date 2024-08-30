package com.studystream.data.service

import com.studystream.data.database.dao.TicketDao
import com.studystream.data.database.tables.TicketTable
import com.studystream.data.database.tables.TicketTypeTable
import com.studystream.data.database.utils.exists
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.data.utils.now
import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile
import com.studystream.domain.model.Ticket
import com.studystream.domain.service.TicketService
import com.studystream.domain.utils.require
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andIfNotNull
import org.jetbrains.exposed.sql.deleteWhere

class TicketServiceImpl(
    private val ticketDao: TicketDao,
    private val ticketTypeDao: TicketDao.TypeDao,
) : TicketService {
    override suspend fun createTicket(
        owner: Account,
        profile: Profile,
        type: Ticket.Type,
        isActivated: Boolean
    ): Result<Ticket> = ioCatchingCall {
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
    ): Result<Ticket.Type> = ioCatchingCall {
        ticketTypeDao.new {
            this.title = title
            this.description = description
            this.totalEvents = totalEvents
            this.durationDays = durationDays
            this.creator = creator
        }
    }

    override suspend fun getTicket(ticketId: Id): Result<Ticket> = ioCatchingCall {
        ticketDao.findById(ticketId).require()
    }

    override suspend fun getTicketType(typeId: Id): Result<Ticket.Type> = ioCatchingCall {
        ticketTypeDao.findById(typeId).require()
    }

    override suspend fun getTickets(filters: TicketService.Filters): List<Ticket> =
        ioCall {
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

    override suspend fun getTicketsTypes(): List<Ticket.Type> = ioCall {
        ticketTypeDao.all().toList()
    }

    override suspend fun existsTicket(ticketId: Id): Boolean = ioCall {
        ticketDao.exists(TicketTable.id eq ticketId)
    }

    override suspend fun existsTicketType(typeId: Id): Boolean = ioCall {
        ticketTypeDao.exists(TicketTypeTable.id eq typeId)
    }

    override suspend fun updateTicket(
        ticket: Ticket,
        profile: Profile,
        type: Ticket.Type,
        isActivated: Boolean
    ): Result<Ticket> = ioCatchingCall {
        ticketDao.findByIdAndUpdate(ticket.idValue) {
            it.profile = profile
            it.type = type

            if (isActivated) {
                if (it.activatedAt == null) {
                    it.activatedAt = LocalDateTime.now()
                }
            } else {
                it.activatedAt = null
            }
        }!!
    }

    override suspend fun updateTicketType(
        type: Ticket.Type,
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?
    ): Result<Ticket.Type> = ioCatchingCall {
        ticketTypeDao.findByIdAndUpdate(type.idValue) {
            it.title = title
            it.description = description
            it.totalEvents = totalEvents
            it.durationDays = durationDays
        }!!
    }

    override suspend fun deleteTicket(ticket: Ticket): Result<Unit> = ioCatchingCall {
        TicketTable.deleteWhere {
            TicketTable.id eq ticket.idValue
        }
    }

    override suspend fun deleteTicketType(type: Ticket.Type): Result<Unit> = ioCatchingCall {
        TicketTypeTable.deleteWhere {
            TicketTypeTable.id eq type.idValue
        }
    }
}