package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.domain.exception.InvalidInputException
import com.studystream.domain.model.Account
import com.studystream.domain.model.MultipartFile
import com.studystream.domain.model.StorageCatalog
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.FileStorageRepository
import com.studystream.domain.repository.ProfileRepository
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
                profileRepository = call.get(),
                fileStorageRepository = call.get(),
            )

            call.respond(HttpStatusCode.Created)
        }
    }
}

suspend fun updateProfileAvatar(
    avatar: MultipartFile,
    route: Profiles.ProfileId.Avatar,
    account: Account,
    profileRepository: ProfileRepository,
    fileStorageRepository: FileStorageRepository,
) = endpoint {
    val profile = profileRepository.getProfile(route.parent.id).getOrThrow()

    if (profile.account.idValue != account.idValue) {
        account.requirePermission(Permission.PROFILES_UPDATE)
    }

    val avatarDocument = fileStorageRepository
        .store(avatar, StorageCatalog.Temp)
        .getOrThrow()

    // TODO: add permissions check
    profileRepository
        .updateAvatar(
            profile = profile,
            avatar = avatarDocument,
        )
        .onSuccess {
            fileStorageRepository.move(avatarDocument, StorageCatalog.Regular).getOrThrow()
        }
        .getOrThrow()
}
