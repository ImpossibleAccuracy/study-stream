package com.studystream.app.server.feature.auth

import com.studystream.app.server.feature.BaseRoutes
import io.ktor.resources.*

@Resource("/auth")
class AuthRoute(
    @Suppress("unused") val parent: BaseRoutes = BaseRoutes()
) {
    @Resource("/signin")
    class SignInRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )

    @Resource("/signup")
    class SignUpRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )

    @Resource("/refresh")
    class RefreshTokenRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )

    @Resource("/me")
    class MeRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )
}