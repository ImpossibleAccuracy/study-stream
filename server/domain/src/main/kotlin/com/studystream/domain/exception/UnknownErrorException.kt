package com.studystream.domain.exception

open class UnknownErrorException(message: String) : ServiceException(message, 500)