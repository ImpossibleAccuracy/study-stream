package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.app.domain.model.Account
import com.studystream.app.domain.security.Permission
import com.studystream.app.domain.service.FileStorageService
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafeGet
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import java.io.File

internal fun Routing.installGetProfileAvatarRoute() {
    authenticate {
        typeSafeGet<Profiles.ProfileId.Avatar> { route ->
            val avatar = getProfileAvatar(
                route = route,
                account = call.requireAccount(),
                profileService = call.get(),
                fileStorageService = call.get(),
            )

            if (avatar == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respondFile(avatar)
            }
        }
    }
}

suspend fun getProfileAvatar(
    route: Profiles.ProfileId.Avatar,
    account: Account,
    profileService: ProfileService,
    fileStorageService: FileStorageService,
): File? = endpoint {
    val profile = profileService.getProfile(route.parent.id).getOrThrow()

    if (profile.account.id != account.id) {
        account.requirePermission(Permission.PROFILES_READ)
    }

    profile.avatar?.let { avatar ->
        fileStorageService
            .getFile(avatar)
            .getOrThrow()
    }
}
