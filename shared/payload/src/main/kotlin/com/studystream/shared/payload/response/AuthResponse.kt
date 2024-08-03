package com.studystream.shared.payload.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val id: Int,
    val token: String,
)