package com.studystream.domain.exception

import com.studystream.domain.exception.ServiceException

class InvalidInputException(message: String) : ServiceException(message, 400)