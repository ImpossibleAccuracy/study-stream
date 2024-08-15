package com.studystream.app.data.database.utils

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Op

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.exists(op: Op<Boolean>): Boolean =
    count(op) != 0L
