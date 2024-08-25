package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.ProfileDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetProfilesListRoute() {
    authenticate {
        typeSafeGet<Profiles.List> { route ->
            val result = getProfilesList(
                route = route,
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getProfilesList(
    route: Profiles.List,
    profileService: ProfileService,
): List<ProfileDto> = endpoint {
    // TODO: add permissions check
    // If account is admin -> can get all profiles
    // Else ownerId is replaced by account.id
    val items = when (route.ownerId) {
        null -> profileService.getProfiles()

        else -> profileService.getProfilesByOwner(route.ownerId)
    }

    items.map {
        it.toDto()
    }
}
