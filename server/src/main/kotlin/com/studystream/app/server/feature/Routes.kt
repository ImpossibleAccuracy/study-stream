package com.studystream.app.server.feature

import com.studystream.app.server.feature.auth.installAuth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureApiRoutes() {
    install(Routing) {
        installAuth()
    }
}
