package com.studystream.app.domain.model

import com.studystream.app.data.database.base.BaseModel
import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.data.database.dao.ProfileDao
import com.studystream.app.data.database.dao.TicketDao
import com.studystream.app.data.database.tables.TicketTable
import com.studystream.app.data.database.tables.TicketTypeTable
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID

class Ticket(id: EntityID<Id>) : BaseModel(id, TicketTable) {
    var owner: Account by AccountDao referencedOn TicketTable.creator
    var profile: Profile by ProfileDao referencedOn TicketTable.profile
    var type: Type by TicketDao.TypeDao referencedOn TicketTable.type

    var activatedAt: LocalDateTime? by TicketTable.activatedAt
    var closedAt: LocalDateTime? by TicketTable.closedAt

    class Type(id: EntityID<Id>) : BaseModel(id, TicketTypeTable) {
        var creator: Account by AccountDao referencedOn TicketTypeTable.creator
        var title: String by TicketTypeTable.title
        var description: String by TicketTypeTable.description

        // TODO: merge into one object
        var totalEvents: Int? by TicketTypeTable.totalEvents
        var durationDays: Int? by TicketTypeTable.durationDays
    }
}