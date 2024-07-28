package com.studystream.data.database.utils

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

suspend inline fun <T> runCatchingTransaction(
    context: CoroutineContext? = Dispatchers.IO,
    db: Database? = null,
    transactionIsolation: Int? = null,
    crossinline statement: suspend Transaction.() -> T
): Result<T> = runSuspendedTransaction(context, db, transactionIsolation) {
    runCatching {
        statement()
    }
}

suspend inline fun <T> runSuspendedTransaction(
    context: CoroutineContext? = Dispatchers.IO,
    db: Database? = null,
    transactionIsolation: Int? = null,
    crossinline statement: suspend Transaction.() -> T
): T = newSuspendedTransaction(context, db, transactionIsolation) {
    statement()
}
