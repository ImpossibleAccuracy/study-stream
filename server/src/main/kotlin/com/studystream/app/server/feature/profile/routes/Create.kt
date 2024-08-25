package com.studystream.app.server.feature.profile.routes

import com.studystream.app.data.database.utils.runSuspendedTransaction
import com.studystream.app.domain.exception.InvalidInputException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.service.AccountService
import com.studystream.app.domain.service.ProfileService
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.typeSafePost
import com.studystream.shared.payload.dto.ProfileDto
import com.studystream.shared.payload.request.CreateProfileRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

internal fun Routing.installCreateProfileRoute() {
    authenticate {
        typeSafePost<Profiles> {
            val result = createProfile(
                body = call.receive(),
                account = call.requireAccount(),
                profileService = call.get(),
                accountService = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createProfile(
    body: CreateProfileRequest,
    account: Account,
    profileService: ProfileService,
    accountService: AccountService,
): ProfileDto = runSuspendedTransaction {
    // TODO: add admin check
    val profileAccount = body.accountId?.let { accountService.getAccount(it).getOrThrow() } ?: account

    if (profileService.existsProfile(
            accountId = profileAccount.idValue,
            name = body.name,
            surname = body.surname,
            patronymic = body.patronymic
        )
    ) {
        throw InvalidInputException("Profile with such name already exists")
    }

    profileService
        .createProfile(
            owner = profileAccount,
            name = body.name,
            surname = body.surname,
            patronymic = body.patronymic,
            birthday = body.birthday,
            avatar = null,
        )
        .getOrThrow()
        .toDto()
}
