package com.studystream.feature.auth

import com.studystream.feature.auth.routes.*
import com.studystream.feature.auth.routes.me.installDeleteMeRoute
import com.studystream.feature.auth.routes.me.installGetMeRoute
import com.studystream.feature.auth.routes.me.installUpdateMeRoute
import io.ktor.server.routing.*

fun Routing.installAuth() {
    installSignInRoute()
    installSignUpRoute()
    installRefreshToken()
    installGetMeRoute()
    installUpdateMeRoute()
    installDeleteMeRoute()
}
