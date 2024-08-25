package com.studystream.app.server.feature.ticket

import com.studystream.app.server.feature.ticket.routes.*
import com.studystream.app.server.feature.ticket.routes.type.*
import io.ktor.server.routing.*

fun Routing.installTicketRoutes() {
    installCreateTicketRoute()
    installGetTicketsListRoute()
    installGetTicketDetailsRoute()
    installUpdateTicketRoute()
    installDeleteTicketRoute()

    installCreateTicketTypeRoute()
    installGetTicketTypeDetailsRoute()
    installGetTicketsTypesListRoute()
    installUpdateTicketTypeRoute()
    installDeleteTicketTypeRoute()
}