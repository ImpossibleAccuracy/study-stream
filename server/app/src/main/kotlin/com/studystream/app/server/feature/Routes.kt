package com.studystream.app.server.feature

import com.studystream.app.server.feature.account.installAccountRoutes
import com.studystream.app.server.feature.auth.installAuthRoutes
import com.studystream.app.server.feature.profile.installProfileRoutes
import com.studystream.app.server.feature.ticket.installTicketRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureApiRoutes() {
    install(Routing) {
        installAuthRoutes()
        installAccountRoutes()
        installProfileRoutes()
        installTicketRoutes()
    }
}
