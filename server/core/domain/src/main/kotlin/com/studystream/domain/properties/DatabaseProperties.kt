package com.studystream.domain.properties

data class DatabaseProperties(
    val url: String,
    val user: String,
    val password: String,
    val poolSize: Int,
)