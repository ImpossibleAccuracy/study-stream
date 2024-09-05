package com.studystream.domain.properties

import com.studystream.domain.properties.feature.AuthProperties
import com.studystream.domain.properties.feature.FileStorageProperties

data class AppProperties(
    val token: TokenProperties,
    val database: DatabaseProperties,
    val auth: AuthProperties,
    val fileStorage: FileStorageProperties
)
