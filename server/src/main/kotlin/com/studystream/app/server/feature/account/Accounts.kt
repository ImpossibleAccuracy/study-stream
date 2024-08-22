package com.studystream.app.server.feature.account

import com.studystream.app.domain.model.Id
import com.studystream.app.server.feature.BaseRoutes
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
    )

    @Serializable
    @Resource("/{id}")
    class AccountId(
        @SerialName("id")
        val id: Id,
        @Suppress("unused") val parent: Accounts = Accounts()
    )
}