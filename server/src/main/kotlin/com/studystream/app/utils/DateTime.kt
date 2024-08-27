package com.studystream.app.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.UTC) =
    Clock.System.now().toLocalDateTime(timeZone)
