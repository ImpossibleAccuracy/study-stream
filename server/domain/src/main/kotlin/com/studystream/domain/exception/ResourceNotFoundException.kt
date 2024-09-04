package com.studystream.domain.exception

class ResourceNotFoundException(message: String) : ServiceException(message, 404)