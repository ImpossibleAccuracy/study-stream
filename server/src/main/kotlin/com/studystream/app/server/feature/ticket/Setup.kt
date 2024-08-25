package com.studystream.app.server.feature.ticket

import com.studystream.app.server.feature.ticket.routes.installCreateTicketRoute
import com.studystream.app.server.feature.ticket.routes.installGetTicketDetailsRoute
import com.studystream.app.server.feature.ticket.routes.installGetTicketsListRoute
import com.studystream.app.server.feature.ticket.routes.type.*
import io.ktor.server.routing.*

fun Routing.installTicketRoutes() {
    installCreateTicketRoute()
    installGetTicketsListRoute()
    installGetTicketDetailsRoute()

    installCreateTicketTypeRoute()
    installGetTicketTypeDetailsRoute()
    installGetTicketsTypesListRoute()
    installUpdateTicketTypeRoute()
    installDeleteTicketTypeRoute()
}