package com.studystream.data.database.utils

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Op


fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.findSingle(op: Op<Boolean>): T? =
    find(op).firstOrNull()
