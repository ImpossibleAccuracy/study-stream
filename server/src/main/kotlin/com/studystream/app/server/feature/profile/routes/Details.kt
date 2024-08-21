package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.shared.payload.dto.ProfileDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetProfileDetailsRoute() {
    authenticate {
        post<Profiles.Id> { route ->
            val result = getProfileDetails(
                id = route.id,
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getProfileDetails(
    id: Id,
    profileService: ProfileService,
): ProfileDto =
    // TODO: add permissions check
    profileService
        .getProfile(id)
        ?.toDto()
        ?: throw ResourceNotFoundException("Profile not found")
