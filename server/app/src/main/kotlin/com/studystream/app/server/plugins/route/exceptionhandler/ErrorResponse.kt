package com.studystream.app.server.plugins.route.exceptionhandler

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("timestamp")
    val timestamp: String = now().format(DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET),
    @SerialName("message")
    val message: String,
)