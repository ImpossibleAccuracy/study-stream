package com.studystream.app.server.feature.profile.routes.avatar

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.service.FileStorageService
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.app.server.utils.typeSafePut
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
                profileId = route.parent.id,
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
    profileId: Id,
    profileService: ProfileService,
    fileStorageService: FileStorageService,
): File? = runSuspendedTransaction {
    val profile = profileService.getProfile(profileId)
        ?: throw ResourceNotFoundException("Profile not found")

    profile.avatar?.let { avatar ->
        fileStorageService
            .getFile(avatar)
            .getOrThrow()
    }
}
