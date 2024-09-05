package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.FileStorageRepository
import com.studystream.domain.repository.ProfileRepository
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
                profileRepository = call.get(),
                fileStorageRepository = call.get(),
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
    profileRepository: ProfileRepository,
    fileStorageRepository: FileStorageRepository,
): File? = endpoint {
    val profile = profileRepository.getProfile(route.parent.id).getOrThrow()

    if (profile.account.idValue != account.idValue) {
        account.requirePermission(Permission.PROFILES_READ)
    }

    profile.avatar?.let { avatar ->
        fileStorageRepository
            .getFile(avatar)
            .getOrThrow()
    }
}
