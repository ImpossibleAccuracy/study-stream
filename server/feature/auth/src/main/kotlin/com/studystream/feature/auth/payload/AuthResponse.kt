package com.studystream.feature.auth.payload

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val id: Int,
    val token: String,
)