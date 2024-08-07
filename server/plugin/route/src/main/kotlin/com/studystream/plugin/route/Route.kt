package com.studystream.plugin.route

import com.studystream.plugin.route.exceptionhandler.installExceptionHandler
import io.ktor.server.application.*
import io.ktor.server.resources.*

fun Application.configureRouting() {
    install(Resources)

    installExceptionHandler()
}

