package com.solovocal.app.routes

import com.solovocal.feature.auth.installAuth
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureApiRoutes() {
    install(Routing) {
        installAuth()
    }
}
