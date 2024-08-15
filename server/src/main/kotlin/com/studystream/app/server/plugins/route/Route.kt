package com.studystream.app.server.plugins.route

import com.studystream.app.server.plugins.route.exceptionhandler.installExceptionHandler
import io.ktor.server.application.*
import io.ktor.server.resources.*

fun Application.configureRouting() {
    install(Resources)

    installExceptionHandler()
}

