package com.studystream.shared.payload.dto

import com.studystream.shared.payload.RemoteId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketTypeDto(
    @SerialName("id")
    val id: RemoteId,
    @SerialName("creator_id")
    val creatorId: RemoteId,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("total_events")
    val totalEvents: Int?,
    @SerialName("duration_days")
    val durationDays: Int?,
)
