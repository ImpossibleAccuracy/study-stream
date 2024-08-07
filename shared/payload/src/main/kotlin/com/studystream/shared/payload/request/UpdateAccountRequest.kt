package com.studystream.shared.payload.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAccountRequest(
    val username: String
)