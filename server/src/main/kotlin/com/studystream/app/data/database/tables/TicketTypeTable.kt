package com.studystream.app.data.database.tables

import com.studystream.app.data.database.base.BaseTable

object TicketTypeTable : BaseTable("ticket_type") {
    val creator = reference("creator_id", AccountTable)
    val title = varchar("title", 255)
    val description = text("description")

    val totalEvents = integer("total_events").nullable()
    val durationDays = integer("duration_days").nullable()
}