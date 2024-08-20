package com.studystream.app.server.feature.profile

import com.studystream.app.server.feature.BaseRoutes
import io.ktor.resources.*

@Resource("/profile")
class ProfileRoute(
    @Suppress("unused") val parent: BaseRoutes = BaseRoutes()
) {
    companion object {
        const val AVATAR_PART_NAME = "avatar"
        const val DEFAULT_AVATAR_FILENAME = "avatar"
        const val DEFAULT_AVATAR_EXTENSION = "jpg"
    }
}