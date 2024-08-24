package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.exception.InvalidInputException
import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.utils.typeSafePut
import com.studystream.shared.payload.dto.ProfileDto
import com.studystream.shared.payload.request.UpdateProfileRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installUpdateProfileRoute() {
    authenticate {
        typeSafePut<Profiles.ProfileId> { route ->
            val result = updateProfile(
                profileId = route.id,
                body = call.receive(),
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun updateProfile(
    profileId: Id,
    body: UpdateProfileRequest,
    profileService: ProfileService,
): ProfileDto {
    val profile = profileService.getProfile(profileId)
        ?: throw ResourceNotFoundException("Profile not found")

    if (profileService.existsProfile(
            accountId = profile.account.idValue,
            name = body.name,
            surname = body.surname,
            patronymic = body.patronymic,
            excludeProfileId = profileId,
        )
    ) {
        throw InvalidInputException("Profile with such name already exists")
    }

    return profileService
        .updateProfile(
            profileId = profileId,
            name = body.name,
            surname = body.surname,
            patronymic = body.patronymic,
            birthday = body.birthday,
        )
        .getOrThrow()
        .toDto()
}
