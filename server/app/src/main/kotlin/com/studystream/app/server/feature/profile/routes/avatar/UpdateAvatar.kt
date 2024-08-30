package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.domain.exception.InvalidInputException
import com.studystream.domain.model.Account
import com.studystream.domain.model.MultipartFile
import com.studystream.domain.model.StorageCatalog
import com.studystream.domain.security.Permission
import com.studystream.domain.service.FileStorageService
import com.studystream.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.security.requirePermission
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.receiveFile
import com.studystream.app.server.utils.typeSafePut
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installUpdateProfileAvatarRoute() {
    authenticate {
        typeSafePut<Profiles.ProfileId.Avatar> { route ->
            updateProfileAvatar(
                avatar = call
                    .receiveFile(
                        Profiles.AVATAR_PART_NAME,
                        Profiles.DEFAULT_AVATAR_FILENAME,
                        Profiles.DEFAULT_AVATAR_EXTENSION
                    )
                    ?: throw InvalidInputException("No avatar presented"),
                route = route,
                account = call.requireAccount(),
                profileService = call.get(),
                fileStorageService = call.get(),
            )

            call.respond(HttpStatusCode.Created)
        }
    }
}

suspend fun updateProfileAvatar(
    avatar: MultipartFile,
    route: Profiles.ProfileId.Avatar,
    account: Account,
    profileService: ProfileService,
    fileStorageService: FileStorageService,
) = endpoint {
    val profile = profileService.getProfile(route.parent.id).getOrThrow()

    if (profile.account.idValue != account.idValue) {
        account.requirePermission(Permission.PROFILES_UPDATE)
    }

    val avatarDocument = fileStorageService
        .store(avatar, StorageCatalog.Temp)
        .getOrThrow()

    // TODO: add permissions check
    profileService
        .updateAvatar(
            profile = profile,
            avatar = avatarDocument,
        )
        .onSuccess {
            fileStorageService.move(avatarDocument, StorageCatalog.Regular).getOrThrow()
        }
        .getOrThrow()
}
