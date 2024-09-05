package com.studystream.domain.properties

data class TokenProperties(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val claimName: String,
    val ttl: Long,
)
