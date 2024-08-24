package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.ProfileDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetProfileDetailsRoute() {
    authenticate {
        typeSafeGet<Profiles.ProfileId> { route ->
            val result = getProfileDetails(
                route = route,
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getProfileDetails(
    route: Profiles.ProfileId,
    profileService: ProfileService,
): ProfileDto =
    // TODO: add permissions check
    profileService
        .getProfile(route.id)
        ?.toDto()
        ?: throw ResourceNotFoundException("Profile not found")
