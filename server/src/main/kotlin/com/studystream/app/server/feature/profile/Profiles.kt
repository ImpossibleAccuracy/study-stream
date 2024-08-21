package com.studystream.app.server.feature.profile

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
    @Resource("/{id}")
    class ProfileId(
        @SerialName("id")
        val id: DomainId,
        @Suppress("unused") val parent: Profiles = Profiles()
    ) {
        @Serializable
        @Resource("/avatar")
        class Avatar(
            val parent: ProfileId,
        )
    }
}