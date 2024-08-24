package com.studystream.shared.payload.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpsertTicketTypeRequest(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("total_events")
    val totalEvents: Int?,
    @SerialName("duration_days")
    val durationDays: Int?,
)