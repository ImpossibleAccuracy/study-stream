package com.studystream.domain.model

import kotlinx.datetime.LocalDateTime

interface BaseModel {
    var idValue: Id

    val createdAt: LocalDateTime
    var updatedAt: LocalDateTime?
}