package com.studystream.shared.payload.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)