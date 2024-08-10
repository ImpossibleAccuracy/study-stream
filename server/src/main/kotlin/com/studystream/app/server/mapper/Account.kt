package com.studystream.app.server.mapper

import com.studystream.app.domain.model.Account
import com.studystream.shared.payload.dto.AccountDto

fun Account.toDto() = AccountDto(
    id = id,
    username = username,
)