package com.solovocal.app

import com.solovocal.app.di.configureKoin
import com.solovocal.app.properties.toAppProperties
import com.solovocal.app.routes.configureApiRoutes
import com.solovocal.plugin.monitoring.configureMonitoring
import com.solovocal.plugin.route.configureRouting
import com.solovocal.plugin.security.configureSecurity
import com.solovocal.plugin.serialization.configureSerialization
import com.solovocal.plugin.sockets.configureSockets
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
