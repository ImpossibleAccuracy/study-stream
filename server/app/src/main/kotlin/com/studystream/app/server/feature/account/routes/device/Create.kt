package com.studystream.app.server.feature.account.routes.device

import com.studystream.domain.model.Account
import com.studystream.domain.repository.DeviceRepository
import com.studystream.app.server.feature.account.Accounts
import com.studystream.app.server.mapper.fromDto
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.endpoint
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.dto.DeviceDto
import com.studystream.shared.payload.request.SaveDeviceRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installSaveDeviceRoute() {
    authenticate {
        typeSafePost<Accounts.Device> {
            val result = createProfile(
                body = call.receive(),
                account = call.requireAccount(),
                deviceRepository = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createProfile(
    body: SaveDeviceRequest,
    account: Account,
    deviceRepository: DeviceRepository,
): DeviceDto = endpoint {
    deviceRepository
        .saveDevice(
            owner = account,
            name = body.name,
            token = body.token,
            type = body.type.fromDto(),
        )
        .getOrThrow()
        .toDto()
}
