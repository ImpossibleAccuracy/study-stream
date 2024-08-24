package com.studystream.app.data.database.extensions.enums

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import kotlin.reflect.KClass

abstract class EnumTable<T : Enum<T>>(
    tableName: String,
    columnName: String = "title",
    columnLength: Int = 255,
    enum: KClass<T>,
) : IntIdTable(tableName) {
    val title: Column<T> = enumerationByName(columnName, columnLength, enum)
}