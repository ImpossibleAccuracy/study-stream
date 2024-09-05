package com.studystream.domain.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val backgroundScope by lazy {
    CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )
}
