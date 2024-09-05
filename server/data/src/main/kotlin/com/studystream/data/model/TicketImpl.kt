package com.studystream.data.model

import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.dao.ProfileDao
import com.studystream.data.database.dao.TicketDao
import com.studystream.data.database.extensions.mapper.withMapper
import com.studystream.data.database.tables.TicketTable
import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import com.studystream.domain.model.Profile
import com.studystream.domain.model.Ticket
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID

class TicketImpl(id: EntityID<Id>) : Ticket, ModelImpl(id, TicketTable) {
    override var owner: Account by AccountDao.referencedOn(TicketTable.creator).withMapper()
    override var profile: Profile by ProfileDao.referencedOn(TicketTable.profile).withMapper()
    override var type: Ticket.Type by TicketDao.TypeDao.referencedOn(TicketTable.type).withMapper()

    override var activatedAt: LocalDateTime? by TicketTable.activatedAt
    override var closedAt: LocalDateTime? by TicketTable.closedAt
}
