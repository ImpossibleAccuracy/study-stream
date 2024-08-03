package com.studystream.feature.auth

import com.studystream.feature.auth.routes.*
import io.ktor.server.routing.*

fun Routing.installAuth() {
    installSignInRoute()
    installSignUpRoute()
    installRefreshToken()
    installGetMeRoute()
    installUpdateMeRoute()
}
