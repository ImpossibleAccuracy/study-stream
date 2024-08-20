package com.studystream.shared.payload.response

import com.studystream.shared.payload.RemoteId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("id")
    val id: RemoteId,
    @SerialName("token")
    val token: String,
)