package com.studystream.app.routes

import com.studystream.feature.auth.installAuth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureApiRoutes() {
    install(Routing) {
        installAuth()
    }
}
