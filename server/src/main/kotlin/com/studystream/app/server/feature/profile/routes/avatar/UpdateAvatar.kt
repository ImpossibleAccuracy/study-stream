package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.app.domain.exception.InvalidInputException
import com.studystream.app.domain.model.MultipartFile
import com.studystream.app.domain.model.StorageCatalog
import com.studystream.app.domain.service.FileStorageService
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
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
    profileService: ProfileService,
    fileStorageService: FileStorageService,
) {
    route.verify(profileService)

    val avatarDocument = fileStorageService
        .store(avatar, StorageCatalog.Temp)
        .getOrThrow()

    // TODO: add permissions check
    return profileService
        .updateAvatar(
            profileId = route.parent.id,
            avatar = avatarDocument,
        )
        .onSuccess {
            fileStorageService.move(avatarDocument, StorageCatalog.Regular).getOrThrow()
        }
        .getOrThrow()
}
