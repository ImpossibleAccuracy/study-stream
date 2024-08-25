package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeDelete
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installDeleteProfileAvatarRoute() {
    authenticate {
        typeSafeDelete<Profiles.ProfileId.Avatar> { route ->
            deleteProfileAvatar(
                route = route,
                profileService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteProfileAvatar(
    route: Profiles.ProfileId.Avatar,
    profileService: ProfileService,
) = endpoint {
    // TODO: add permissions check
    profileService
        .updateAvatar(
            profile = profileService.getProfile(route.parent.id).getOrThrow(),
            avatar = null,
        )
        .getOrThrow()
}
