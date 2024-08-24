package com.studystream.app.server.feature.profile

import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.BaseRoutes
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.studystream.app.domain.model.Id as DomainId

@Resource("/profile")
class Profiles(
    @Suppress("unused") val parent: BaseRoutes = BaseRoutes()
) {
    companion object {
        const val AVATAR_PART_NAME = "avatar"
        const val DEFAULT_AVATAR_FILENAME = "avatar"
        const val DEFAULT_AVATAR_EXTENSION = "jpg"
    }

    @Serializable
    @Resource("")
    class List(
        @SerialName("owner_id")
        val ownerId: Id? = null,
        @Suppress("unused") val parent: Profiles = Profiles()
    )

    @Serializable
    @Resource("/{id}")
    class ProfileId(
        @SerialName("id")
        val id: DomainId,
        @Suppress("unused") val parent: Profiles = Profiles()
    ) {
        suspend fun verify(profileService: ProfileService) {
            if (!profileService.existsProfile(id)) {
                throw ResourceNotFoundException("Profile not found")
            }
        }

        @Serializable
        @Resource("/avatar")
        class Avatar(
            val parent: ProfileId,
        ) {
            suspend fun verify(profileService: ProfileService) {
                parent.verify(profileService)
            }
        }
    }
}