package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.utils.typeSafeDelete
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installDeleteProfileRoute() {
    authenticate {
        typeSafeDelete<Profiles.ProfileId> { route ->
            deleteProfile(
                route = route,
                profileService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteProfile(
    route: Profiles.ProfileId,
    profileService: ProfileService,
) {
    route.verify(profileService)

    // TODO: add permissions check
    return profileService
        .deleteProfile(
            profileId = route.id,
        )
        .getOrThrow()
}
