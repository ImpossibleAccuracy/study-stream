package com.studystream.domain.exception

class OperationRejectedException(message: String) : ServiceException(message, 403)