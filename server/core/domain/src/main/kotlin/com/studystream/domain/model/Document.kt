package com.studystream.domain.model

import java.time.Instant

data class Document(
    val id: Int,
    var createdAt: Instant = Instant.now(),
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
