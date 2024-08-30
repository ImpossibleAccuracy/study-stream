package com.studystream.data.database.dao

import com.studystream.data.database.base.BaseDao
import com.studystream.data.database.tables.*
import com.studystream.data.model.*
import org.jetbrains.exposed.dao.IntEntityClass

// Reference to model class is needed to "Exposed" things

object AccountDao : BaseDao<AccountImpl>(AccountTable, AccountImpl::class)

object ProfileDao : BaseDao<ProfileImpl>(ProfileTable, ProfileImpl::class)

object DocumentDao : BaseDao<DocumentImpl>(DocumentTable, DocumentImpl::class)

object DocumentTypeDao : BaseDao<DocumentTypeImpl>(DocumentTypeTable, DocumentTypeImpl::class)

object DeviceDao : BaseDao<DeviceImpl>(DeviceTable, DeviceImpl::class)

object TicketDao : BaseDao<TicketImpl>(TicketTable, TicketImpl::class) {
    object TypeDao : BaseDao<TicketTypeImpl>(TicketTypeTable, TicketTypeImpl::class)
}

object RoleDao : IntEntityClass<AccountRole>(RoleTable, AccountRole::class.java)

object PrivilegeDao : IntEntityClass<Privilege>(PrivilegeTable, Privilege::class.java)
