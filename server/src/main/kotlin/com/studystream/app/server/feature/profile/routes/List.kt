package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.shared.payload.dto.ProfileDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetProfilesListRoute() {
    authenticate {
        post<Profiles> {
            val result = getProfilesList(
                account = call.requireAccount(),
                profileService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getProfilesList(
    account: Account,
    profileService: ProfileService,
): List<ProfileDto> =
    // TODO: add permissions check
    profileService
        .getProfilesByOwner(account.id.value)
        .map {
            it.toDto()
        }
