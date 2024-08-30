package com.studystream.data.model

import com.studystream.data.database.base.ModelImpl
import com.studystream.data.database.dao.AccountDao
import com.studystream.data.database.extensions.mapper.withMapper
import com.studystream.data.database.tables.TicketTypeTable
import com.studystream.domain.model.Account
import com.studystream.domain.model.Id
import com.studystream.domain.model.Ticket
import org.jetbrains.exposed.dao.id.EntityID

class TicketTypeImpl(id: EntityID<Id>) : Ticket.Type, ModelImpl(id, TicketTypeTable) {
    override var title: String by TicketTypeTable.title
    override var description: String by TicketTypeTable.description
    override var totalEvents: Int? by TicketTypeTable.totalEvents
    override var durationDays: Int? by TicketTypeTable.durationDays

    override var creator: Account by AccountDao.referencedOn(TicketTypeTable.creator).withMapper()
}