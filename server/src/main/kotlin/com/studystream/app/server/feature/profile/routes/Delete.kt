package com.studystream.app.server.feature.profile.routes

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.security.Permission
import com.studystream.app.domain.service.ProfileService
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
                profileService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteProfile(
    route: Profiles.ProfileId,
    account: Account,
    profileService: ProfileService,
) = endpoint {
    val profile = profileService.getProfile(route.id).getOrThrow()

    if (profile.account.idValue != account.idValue) {
        account.requirePermission(Permission.PROFILES_DELETE)
    }

    profileService
        .deleteProfile(
            profile = profile,
        )
        .getOrThrow()
}
