package com.studystream.app.domain.exception

class InvalidInputException(message: String) : ServiceException(message, 400)