package com.studystream.domain.exception

import com.studystream.domain.exception.ServiceException

class OperationRejectedException(message: String) : ServiceException(message, 403)