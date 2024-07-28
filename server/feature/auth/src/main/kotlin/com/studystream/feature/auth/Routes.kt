package com.studystream.feature.auth

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/auth")
class AuthRoute {
    @Serializable
    @Resource("/signIn")
    class SignInRoute(
        @Suppress("unused") val parent: AuthRoute = AuthRoute()
    )
}