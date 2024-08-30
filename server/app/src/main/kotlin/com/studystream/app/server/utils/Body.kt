package com.studystream.app.server.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LazyBody<T : Any>(
    private val source: suspend () -> T,
) {
    private var _value: T? = null

    private val mutex = Mutex()

    suspend operator fun invoke(): T =
        mutex.withLock {
            val value = _value
            if (value != null) return value

            val newValue = this.source()
            _value = newValue
            return newValue
        }
}
