package com.studystream.app.server.feature.profile

import com.studystream.app.server.feature.profile.routes.avatar.installDeleteProfileAvatarRoute
import com.studystream.app.server.feature.profile.routes.installDeleteProfileRoute
import com.studystream.app.server.feature.profile.routes.avatar.installGetProfileAvatarRoute
import com.studystream.app.server.feature.profile.routes.avatar.installUpdateProfileAvatarRoute
import com.studystream.app.server.feature.profile.routes.installCreateProfileRoute
import com.studystream.app.server.feature.profile.routes.installGetProfileDetailsRoute
import com.studystream.app.server.feature.profile.routes.installGetProfilesListRoute
import com.studystream.app.server.feature.profile.routes.installUpdateProfileRoute
import io.ktor.server.routing.*

fun Routing.installProfileRoutes() {
    installCreateProfileRoute()
    installGetProfilesListRoute()
    installGetProfileDetailsRoute()
    installUpdateProfileRoute()
    installDeleteProfileRoute()

    installUpdateProfileAvatarRoute()
    installGetProfileAvatarRoute()
    installDeleteProfileAvatarRoute()
}