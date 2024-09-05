package com.studystream.app.server.feature.account

import com.studystream.domain.model.Id
import com.studystream.app.server.feature.BaseRoutes
import com.studystream.shared.payload.dto.DeviceTypeDto
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Resource("/account")
class Accounts(
    @Suppress("unused") val parent: BaseRoutes = BaseRoutes()
) {
    @Resource("/device")
    class Device(
        @Suppress("unused") val parent: Accounts = Accounts()
    ) {
        @Serializable
        @Resource("")
        class List(
            @SerialName("owner_id")
            val ownerId: Id? = null,
            @SerialName("device_type")
            val deviceType: DeviceTypeDto? = null,
            @Suppress("unused") val parent: Device = Device()
        )
    }

    @Serializable
    @Resource("")
    class List(
        @Suppress("unused") val parent: Accounts = Accounts()
    )

    @Serializable
    @Resource("/{id}")
    class AccountId(
        @SerialName("id")
        val id: Id,
        @Suppress("unused") val parent: Accounts = Accounts()
    )
}