package com.studystream.shared.payload.request

import com.studystream.shared.payload.RemoteId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTicketRequest(
    @SerialName("profile_id")
    val profileId: RemoteId,
    @SerialName("type_id")
    val typeId: RemoteId,
    @SerialName("is_activated")
    val isActivated: Boolean? = null,
)
