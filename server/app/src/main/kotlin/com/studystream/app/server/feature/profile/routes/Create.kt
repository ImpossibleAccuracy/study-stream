package com.studystream.app.server.feature.profile.routes

import com.studystream.domain.exception.InvalidInputException
import com.studystream.domain.model.Account
import com.studystream.domain.security.Permission
import com.studystream.domain.repository.AccountRepository
import com.studystream.domain.repository.ProfileRepository
import com.studystream.app.server.feature.profile.Profiles
import com.studystream.app.server.mapper.toDto
import com.studystream.app.server.security.choiceIdByPermission
import com.studystream.app.server.security.requireAccount
import com.studystream.app.server.utils.endpoint
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
                profileRepository = call.get(),
                accountRepository = call.get(),
            )

            call.respond(result)
        }
    }
}

suspend fun createProfile(
    body: CreateProfileRequest,
    account: Account,
    profileRepository: ProfileRepository,
    accountRepository: AccountRepository,
): ProfileDto = endpoint {
    val profileAccount = accountRepository
        .getAccount(
            account.choiceIdByPermission(
                permission = Permission.PROFILES_CREATE,
                requestedId = body.accountId,
            )
        )
        .getOrThrow()

    if (profileRepository.existsProfile(
            accountId = profileAccount.idValue,
            name = body.name,
            surname = body.surname,
            patronymic = body.patronymic
        )
    ) {
        throw InvalidInputException("Profile with such name already exists")
    }

    profileRepository
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
