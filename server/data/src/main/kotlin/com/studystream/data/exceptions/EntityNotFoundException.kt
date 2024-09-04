package com.studystream.data.exceptions

import com.studystream.domain.exception.UnknownErrorException

class EntityNotFoundException : UnknownErrorException("Entity not found")
