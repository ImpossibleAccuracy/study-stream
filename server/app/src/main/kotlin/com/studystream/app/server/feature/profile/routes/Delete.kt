package com.studystream.app.server.feature.profile.routes

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.ProfileRepository
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
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
                account = call.requireAccount(),
                profileRepository = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteProfile(
    route: Profiles.ProfileId,
    account: Account,
    profileRepository: ProfileRepository,
) = endpoint {
    val profile = profileRepository.getProfile(route.id).getOrThrow()

    if (profile.account.idValue != account.idValue) {
        account.requirePermission(Permission.PROFILES_DELETE)
    }

    profileRepository
        .deleteProfile(
            profile = profile,
        )
        .getOrThrow()
}
