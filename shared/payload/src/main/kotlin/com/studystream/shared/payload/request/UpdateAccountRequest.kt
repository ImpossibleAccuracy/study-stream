package com.studystream.shared.payload.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAccountRequest(
    @SerialName("username")
    val username: String
)