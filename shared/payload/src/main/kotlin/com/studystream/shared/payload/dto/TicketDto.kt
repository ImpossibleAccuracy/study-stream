package com.studystream.shared.payload.dto

import com.studystream.shared.payload.RemoteId
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketDto(
    @SerialName("id")
    val id: RemoteId,
    @SerialName("owner_id")
    var ownerId: RemoteId,
    @SerialName("profile_id")
    var profileId: RemoteId,
    @SerialName("type_id")
    var typeId: RemoteId,
    @SerialName("activated_at")
    var activatedAt: LocalDateTime?,
    @SerialName("closed_at")
    var closedAt: LocalDateTime?,
)
