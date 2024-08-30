package com.studystream.app.data.database.dao

import com.studystream.app.data.database.base.BaseDao
import com.studystream.app.data.database.tables.*
import com.studystream.app.domain.model.*
import org.jetbrains.exposed.dao.IntEntityClass

// Reference to model class is needed to "Exposed" things

object AccountDao : BaseDao<Account>(AccountTable, Account::class)

object ProfileDao : BaseDao<Profile>(ProfileTable, Profile::class)

object DocumentDao : BaseDao<Document>(DocumentTable, Document::class)

object DocumentTypeDao : BaseDao<Document.Type>(DocumentTypeTable, Document.Type::class)

object DeviceDao : BaseDao<Device>(DeviceTable, Device::class)

object TicketDao : BaseDao<Ticket>(TicketTable, Ticket::class) {
    object TypeDao : BaseDao<Ticket.Type>(TicketTypeTable, Ticket.Type::class)
}

object RoleDao : IntEntityClass<AccountRole>(RoleTable, AccountRole::class.java)

object PrivilegeDao : IntEntityClass<Privilege>(PrivilegeTable, Privilege::class.java)
