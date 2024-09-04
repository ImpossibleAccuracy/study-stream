package com.studystream.domain.exception

class InvalidInputException(message: String) : ServiceException(message, 400)