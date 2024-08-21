package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.utils.typeSafeDelete
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installDeleteProfileAvatarRoute() {
    authenticate {
        typeSafeDelete<Profiles.Id> { route ->
            deleteProfileAvatar(
                profileId = route.id,
                profileService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteProfileAvatar(
    profileId: Id,
    profileService: ProfileService,
) {
    if (!profileService.existsProfile(profileId)) {
        throw ResourceNotFoundException("Profile not found")
    }

    // TODO: add permissions check
    return profileService
        .updateAvatar(
            profileId = profileId,
            avatar = null,
        )
        .getOrThrow()
}
