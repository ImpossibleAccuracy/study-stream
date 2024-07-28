package com.studystream.feature.auth

import com.studystream.feature.auth.routes.installSignInRoute
import io.ktor.server.routing.*

fun Routing.installAuth() {
    installSignInRoute()
}
