package com.studystream.app.server.utils

import com.studystream.app.data.database.utils.runSuspendedTransaction
import kotlinx.coroutines.Dispatchers

suspend inline fun <T> endpoint(crossinline block: suspend () -> T): T =
    runSuspendedTransaction(context = Dispatchers.Default) {
        block()
    }
