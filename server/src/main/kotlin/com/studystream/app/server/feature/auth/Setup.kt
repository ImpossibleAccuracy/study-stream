package com.studystream.app.server.feature.auth

import com.studystream.app.server.feature.auth.routes.installRefreshToken
import com.studystream.app.server.feature.auth.routes.installSignInRoute
import com.studystream.app.server.feature.auth.routes.installSignUpRoute
import com.studystream.app.server.feature.auth.routes.me.installGetMeRoute
import com.studystream.app.server.feature.auth.routes.me.installUpdateMeRoute
import io.ktor.server.routing.*

fun Routing.installAuthRoutes() {
    installSignInRoute()
    installSignUpRoute()
    installRefreshToken()
    installGetMeRoute()
    installUpdateMeRoute()
}
