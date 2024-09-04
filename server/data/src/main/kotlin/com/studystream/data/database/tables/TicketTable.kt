package com.studystream.data.database.tables

import com.studystream.data.database.base.BaseTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object TicketTable : BaseTable("ticket") {
    val creator = reference("owner_id", AccountTable)
    val profile = reference("profile_id", ProfileTable)
    val type = reference("type_id", TicketTypeTable)

    val activatedAt = datetime("activated_at").nullable()
    val closedAt = datetime("closed_at").nullable()
}