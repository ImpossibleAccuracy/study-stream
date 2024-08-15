package com.studystream.app.domain.exception

class OperationRejectedException(message: String) : ServiceException(message, 403)