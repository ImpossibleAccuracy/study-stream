package com.studystream.shared.payload.request

import com.studystream.shared.payload.RemoteId
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileRequest(
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("patronymic")
    val patronymic: String? = null,
    @SerialName("birthday")
    val birthday: LocalDate,
    @SerialName("account_id")
    val accountId: RemoteId? = null,
)
