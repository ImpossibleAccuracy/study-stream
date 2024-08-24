package com.studystream.app.server.feature.ticket

import com.studystream.app.server.feature.ticket.routes.installGetTicketsListRoute
import io.ktor.server.routing.*

fun Routing.installTicketRoutes() {
    installGetTicketsListRoute()
}