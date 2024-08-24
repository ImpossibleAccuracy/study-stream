package com.studystream.app.data.database.extensions.enums

import com.studystream.app.domain.model.Id
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.Reference
import org.jetbrains.exposed.dao.id.EntityID
import kotlin.reflect.KProperty

class EnumColumnDelegate<E : Enum<E>, REF : EnumDao<E>>(
    private val companion: EnumDaoCompanion<E, REF>,
    private val reference: Reference<EntityID<Id>, Id, REF>,
) {
    private val table: EnumTable<E>

    init {
        val refColumn = reference.reference.referee<EntityID<Id>>()!!

        @Suppress("UNCHECKED_CAST")
        table = refColumn.table as? EnumTable<E>
            ?: throw IllegalArgumentException("Referenced table has wrong supertype. Check that your table is nested from EnumTable.")
    }

    operator fun getValue(thisRef: Entity<Id>, property: KProperty<*>): E = with(thisRef) {
        val value = reference.getValue(thisRef, property)

        value.title
    }

    operator fun setValue(thisRef: Entity<Id>, property: KProperty<*>, value: E) = with(thisRef) {
        reference.setValue(thisRef, property, findOrCreateEnum(value))
    }

    private fun findOrCreateEnum(value: E): EnumDao<E> =
        companion
            .find {
                table.title eq value
            }
            .firstOrNull()
            ?: companion.new {
                this.title = value
            }
}

infix fun <E : Enum<E>, REF : EnumDao<E>> Reference<EntityID<Id>, Id, REF>.asEnum(
    companion: EnumDaoCompanion<E, REF>,
) = EnumColumnDelegate(companion, this)
