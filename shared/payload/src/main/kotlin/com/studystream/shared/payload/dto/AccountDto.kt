package com.studystream.shared.payload.dto

import com.studystream.shared.payload.RemoteId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("id")
    val id: RemoteId,
    @SerialName("username")
    val username: String,
)