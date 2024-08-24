package com.studystream.app.server.feature.account.routes.device

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.DeviceService
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.mapper.fromDto
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.typeSafeGet
import com.studystream.shared.payload.dto.DeviceDto
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get


internal fun Routing.installGetDevicesListRoute() {
    authenticate {
        typeSafeGet<Accounts.Device.List> { route ->
            val result = getDevicesList(
                route = route,
                account = call.requireAccount(),
                deviceService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun getDevicesList(
    route: Accounts.Device.List,
    account: Account,
    deviceService: DeviceService,
): List<DeviceDto> = runSuspendedTransaction {
    // TODO: add permissions check
    // If account is admin -> can get all devices
    // Else ownerId is replaced by account.id
    deviceService
        .getDevices(
            filters = DeviceService.Filters(
                ownerId = route.ownerId ?: account.idValue,
                deviceType = route.deviceType.fromDto().nullIfNotSpecified(),
            )
        )
        .map {
            it.toDto()
        }
}
