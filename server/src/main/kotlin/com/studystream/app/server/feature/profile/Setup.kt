package com.studystream.app.server.feature.profile

import com.studystream.app.server.feature.profile.routes.*
import com.studystream.app.server.feature.profile.routes.installCreateProfileRoute
import com.studystream.app.server.feature.profile.routes.avatar.installDeleteProfileAvatarRoute
import com.studystream.app.server.feature.profile.routes.installGetProfileDetailsRoute
import com.studystream.app.server.feature.profile.routes.avatar.installUpdateProfileAvatarRoute
import io.ktor.server.routing.*

fun Routing.installProfileRoutes() {
    installCreateProfileRoute()
    installGetProfileDetailsRoute()
    installUpdateProfileAvatarRoute()
    installDeleteProfileAvatarRoute()
    installGetProfilesListRoute()
}