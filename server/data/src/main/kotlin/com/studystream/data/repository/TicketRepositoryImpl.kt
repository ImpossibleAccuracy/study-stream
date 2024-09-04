package com.studystream.data.repository

import com.studystream.data.datasource.base.TicketDataSource
import com.studystream.data.datasource.base.TicketTypeDataSource
import com.studystream.data.utils.ioCall
import com.studystream.data.utils.ioCatchingCall
import com.studystream.data.utils.now
import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile
import com.studystream.domain.model.Ticket
import com.studystream.domain.repository.TicketRepository
import com.studystream.domain.utils.require
import kotlinx.datetime.LocalDateTime

class TicketRepositoryImpl(
    private val ticketDataSource: TicketDataSource,
    private val ticketTypeDataSource: TicketTypeDataSource,
) : TicketRepository {
    override suspend fun createTicket(
        owner: Account,
        profile: Profile,
        type: Ticket.Type,
        isActivated: Boolean
    ): Result<Ticket> = ioCatchingCall {
        ticketDataSource.save {
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
        ticketTypeDataSource.save {
            this.title = title
            this.description = description
            this.totalEvents = totalEvents
            this.durationDays = durationDays
            this.creator = creator
        }
    }

    override suspend fun getTicket(ticketId: Id): Result<Ticket> = ioCatchingCall {
        ticketDataSource.findById(ticketId).require()
    }

    override suspend fun getTicketType(typeId: Id): Result<Ticket.Type> = ioCatchingCall {
        ticketTypeDataSource.findById(typeId).require()
    }

    override suspend fun getTickets(filters: TicketRepository.Filters): List<Ticket> = ioCall {
        ticketDataSource.search(filters.ownerId, filters.profileId, filters.typeId)
    }

    override suspend fun getTicketsTypes(): List<Ticket.Type> = ioCall {
        ticketTypeDataSource.getAll()
    }

    override suspend fun existsTicket(ticketId: Id): Boolean = ioCall {
        ticketDataSource.existsById(ticketId)
    }

    override suspend fun existsTicketType(typeId: Id): Boolean = ioCall {
        ticketTypeDataSource.existsById(typeId)
    }

    override suspend fun updateTicket(
        ticket: Ticket,
        profile: Profile,
        type: Ticket.Type,
        isActivated: Boolean
    ): Result<Ticket> = ioCatchingCall {
        ticketDataSource.save(ticket.idValue) {
            this.profile = profile
            this.type = type

            if (isActivated) {
                if (this.activatedAt == null) {
                    this.activatedAt = LocalDateTime.now()
                }
            } else {
                this.activatedAt = null
            }
        }
    }

    override suspend fun updateTicketType(
        type: Ticket.Type,
        title: String,
        description: String,
        totalEvents: Int?,
        durationDays: Int?
    ): Result<Ticket.Type> = ioCatchingCall {
        ticketTypeDataSource.save(type.idValue) {
            this.title = title
            this.description = description
            this.totalEvents = totalEvents
            this.durationDays = durationDays
        }
    }

    override suspend fun deleteTicket(ticket: Ticket): Result<Unit> = ioCatchingCall {
        ticketDataSource.deleteById(ticket.idValue)
    }

    override suspend fun deleteTicketType(type: Ticket.Type): Result<Unit> = ioCatchingCall {
        ticketTypeDataSource.deleteById(type.idValue)
    }
}