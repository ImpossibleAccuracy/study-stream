package com.studystream.app.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> ioCall(block: suspend () -> T): Result<T> = runCatching {
    withContext(Dispatchers.IO) {
        block()
    }
}