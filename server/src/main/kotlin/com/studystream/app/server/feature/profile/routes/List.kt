package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.security.Permission
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.hasPermission
import com.studystream.app.server.security.requireAccount
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
                account = call.requireAccount(),
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getProfilesList(
    route: Profiles.List,
    account: Account,
    profileService: ProfileService,
): List<ProfileDto> = endpoint {
    val canReadAllProfiles = account.hasPermission(Permission.PROFILES_READ)

    val items = if (route.ownerId == null && canReadAllProfiles) {
        profileService.getProfiles()
    } else {
        profileService.getProfilesByOwner(
            ownerId = when {
                canReadAllProfiles -> route.ownerId ?: account.idValue
                else -> account.idValue
            }
        )
    }

    items.map {
        it.toDto()
    }
}
