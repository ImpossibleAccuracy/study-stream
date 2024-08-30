package com.studystream.app.server.plugins.security

import io.ktor.server.application.*

fun Application.configureSecurity() {
    createAllRolesAndPrivileges()
    addAuthentication()
}

