package com.studystream.app.server.utils

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend inline fun <T> endpoint(crossinline block: suspend () -> T): T =
    newSuspendedTransaction(context = Dispatchers.Default) {
        block()
    }
