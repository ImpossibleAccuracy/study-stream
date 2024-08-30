package com.studystream.app.server.plugins.sockets

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.serialization.serializer

inline fun <reified T : Any> Route.webSocket(
    webSocketProtocol: String? = null,
    noinline handler: suspend DefaultWebSocketServerSession.() -> Unit
) {
    val serializer = serializer<T>()

    val resources = application.plugin(Resources)
    val path = resources.resourcesFormat.encodeToPathPattern(serializer)

    webSocket(path = path, protocol = webSocketProtocol, handler = handler)
}
