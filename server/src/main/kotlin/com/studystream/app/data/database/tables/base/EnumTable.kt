package com.studystream.app.data.database.tables.base

import org.jetbrains.exposed.dao.id.IntIdTable
import kotlin.reflect.KClass

abstract class EnumTable<T : Enum<T>>(
    tableName: String,
    columnName: String = "title",
    columnLength: Int = 255,
    enum: KClass<T>,
) : IntIdTable(tableName) {
    val title = enumerationByName(columnName, columnLength, enum)
}