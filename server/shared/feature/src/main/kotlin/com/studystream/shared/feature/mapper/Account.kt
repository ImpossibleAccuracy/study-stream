package com.studystream.shared.feature.mapper

import com.studystream.domain.model.Account
import com.studystream.shared.payload.dto.AccountDto

fun Account.toDto() = AccountDto(
    id = id,
    username = username,
)