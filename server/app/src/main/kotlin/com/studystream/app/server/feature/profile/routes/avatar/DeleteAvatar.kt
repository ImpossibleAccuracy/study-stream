package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.service.ProfileService
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

internal fun Routing.installDeleteProfileAvatarRoute() {
    authenticate {
        typeSafeDelete<Profiles.ProfileId.Avatar> { route ->
            deleteProfileAvatar(
                route = route,
                account = call.requireAccount(),
                profileService = call.get(),
            )

            call.respond(HttpStatusCode.NoContent)
        }
    }
}

suspend fun deleteProfileAvatar(
    route: Profiles.ProfileId.Avatar,
    account: Account,
    profileService: ProfileService,
) = endpoint {
    val profile = profileService.getProfile(route.parent.id).getOrThrow()

    if (profile.account.idValue != account.idValue) {
        account.requirePermission(Permission.PROFILES_UPDATE)
    }

    profileService
        .updateAvatar(
            profile = profile,
            avatar = null,
        )
        .getOrThrow()
}
