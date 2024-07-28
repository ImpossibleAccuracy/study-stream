package com.studystream.domain.exception

open class ServiceException(
    message: String,
    val status: Int,
) : RuntimeException(message)