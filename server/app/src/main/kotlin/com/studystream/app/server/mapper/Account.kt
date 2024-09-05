package com.studystream.app.server.mapper

import com.studystream.domain.model.Account
import com.studystream.shared.payload.dto.AccountDto

fun Account.toDto() = AccountDto(
    id = idValue,
    username = username,
)