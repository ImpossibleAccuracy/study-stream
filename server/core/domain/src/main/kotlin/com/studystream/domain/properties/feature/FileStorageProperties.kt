package com.studystream.domain.properties.feature

data class FileStorageProperties(
    val tempStorePath: String,
    val regularStorePath: String,
    val fileNamePattern: String,
    val maxFilenameLength: Int? = null,
)
