package com.studystream.app.server.feature.profile.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
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
                account = call.requireAccount(),
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getProfileDetails(
    route: Profiles.ProfileId,
    account: Account,
    profileService: ProfileService,
): ProfileDto = endpoint {
    profileService
        .getProfile(route.id)
        .getOrThrow()
        .also {
            if (it.account.idValue != account.idValue) {
                account.requirePermission(Permission.PROFILES_READ)
            }
        }
        .toDto()
}
