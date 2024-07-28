package com.studystream.app

import com.studystream.app.di.configureKoin
import com.studystream.app.properties.toAppProperties
import com.studystream.app.routes.configureApiRoutes
import com.studystream.plugin.monitoring.configureMonitoring
import com.studystream.plugin.route.configureRouting
import com.studystream.plugin.security.configureSecurity
import com.studystream.plugin.serialization.configureSerialization
import com.studystream.plugin.sockets.configureSockets
import io.ktor.server.application.*
import org.koin.ktor.ext.get

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val properties = environment.toAppProperties()

    configureKoin(properties)
    configureMonitoring()
    configureSerialization()
    configureSecurity(
        tokenProperties = properties.token,
        authService = get()
    )
    configureSockets()
    configureRouting()
    configureApiRoutes()
}
