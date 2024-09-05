package com.studystream.app.server.plugins.monitoring

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.Level

fun Application.configureMonitoring() {
    if (environment.developmentMode) {
        install(CallLogging) {
            level = Level.DEBUG
        }
    }
}
