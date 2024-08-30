package com.studystream.domain.exception

import com.studystream.domain.exception.ServiceException

class ResourceNotFoundException(message: String) : ServiceException(message, 404)