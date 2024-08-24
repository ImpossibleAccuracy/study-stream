package com.studystream.app.data.database.tables

import com.studystream.app.data.database.base.BaseTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object TicketTable : BaseTable("ticket") {
    val creator = reference("creator_id", AccountTable)
    val profile = reference("profile_id", ProfileTable)
    val type = reference("type_id", TicketTypeTable)

    val activatedAt = datetime("activated_at").nullable()
    val closedAt = datetime("closed_at").nullable()
}