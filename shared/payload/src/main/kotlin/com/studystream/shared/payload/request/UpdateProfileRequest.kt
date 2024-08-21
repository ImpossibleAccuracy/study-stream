package com.studystream.shared.payload.request

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("patronymic")
    val patronymic: String? = null,
    @SerialName("birthday")
    val birthday: LocalDate,
)
