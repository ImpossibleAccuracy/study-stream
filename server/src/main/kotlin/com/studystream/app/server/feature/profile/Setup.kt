package com.studystream.app.server.feature.profile

import com.studystream.app.server.feature.profile.routes.installCreateProfileRoute
import io.ktor.server.routing.*

fun Routing.installProfileRoutes() {
    installCreateProfileRoute()
}