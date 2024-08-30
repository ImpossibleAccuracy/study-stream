package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.exception.InvalidInputException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.security.Permission
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
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
                route = route,
                account = call.requireAccount(),
                body = call.receive(),
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun updateProfile(
    route: Profiles.ProfileId,
    account: Account,
    body: UpdateProfileRequest,
    profileService: ProfileService,
): ProfileDto = endpoint {
    val profile = profileService.getProfile(route.id).getOrThrow()

    if (profile.account.idValue != account.idValue) {
        account.requirePermission(Permission.PROFILES_UPDATE)
    }

    if (profileService.existsProfile(
            accountId = profile.account.idValue,
            name = body.name,
            surname = body.surname,
            patronymic = body.patronymic,
            excludeProfileId = route.id,
        )
    ) {
        throw InvalidInputException("Profile with such name already exists")
    }

    profileService
        .updateProfile(
            profile = profile,
            name = body.name,
            surname = body.surname,
            patronymic = body.patronymic,
            birthday = body.birthday,
        )
        .getOrThrow()
        .toDto()
}
