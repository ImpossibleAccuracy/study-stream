package com.studystream.feature.auth

import com.studystream.feature.auth.routes.installRefreshToken
import com.studystream.feature.auth.routes.installSignInRoute
import com.studystream.feature.auth.routes.installSignUpRoute
import io.ktor.server.routing.*

fun Routing.installAuth() {
    installSignInRoute()
    installSignUpRoute()
    installRefreshToken()
}
