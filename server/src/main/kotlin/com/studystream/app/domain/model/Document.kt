package com.studystream.app.domain.model

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant

data class Document(
    val id: Int,
    var createdAt: Instant = now(),
    var title: String,
    var hash: String,
    var path: String,
    var type: Type,
) {
    data class Type(
        val id: Int,
        val title: String,
        val mimeType: String,
    )
}
