package com.studystream.app

import com.studystream.app.properties.toAppProperties
import com.studystream.app.server.feature.configureApiRoutes
import com.studystream.app.server.plugins.koin.configureKoin
import com.studystream.app.server.plugins.monitoring.configureMonitoring
import com.studystream.app.server.plugins.route.configureRouting
import com.studystream.app.server.plugins.security.configureSecurity
import com.studystream.app.server.plugins.serialization.configureSerialization
import com.studystream.app.server.plugins.sockets.configureSockets
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val properties = environment.toAppProperties()

    configureKoin(properties)
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureSockets()
    configureRouting()
    configureApiRoutes()
}
