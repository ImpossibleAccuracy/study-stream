package com.studystream.app.server.mapper

import com.studystream.domain.model.Profile
import com.studystream.shared.payload.dto.ProfileDto

fun Profile.toDto() = ProfileDto(
    id = idValue,
    name = name,
    surname = surname,
    patronymic = patronymic,
    birthday = birthday,
    accountId = account.idValue,
    avatarId = avatar?.idValue,
)