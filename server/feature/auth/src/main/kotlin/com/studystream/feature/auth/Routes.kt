package com.studystream.feature.auth

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/auth")
class AuthRoute {
    @Serializable
    @Resource("/signin")
    class SignInRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )

    @Serializable
    @Resource("/signup")
    class SignUpRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )

    @Serializable
    @Resource("/refresh")
    class RefreshTokenRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )

    @Serializable
    @Resource("/me")
    class GetMeRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )
}