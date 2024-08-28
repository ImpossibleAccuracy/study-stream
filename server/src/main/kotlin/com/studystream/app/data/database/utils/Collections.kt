package com.studystream.app.data.database.utils

import org.jetbrains.exposed.sql.SizedCollection

fun <T> List<T>.toSizedCollection(): SizedCollection<T> =
    SizedCollection(this)
