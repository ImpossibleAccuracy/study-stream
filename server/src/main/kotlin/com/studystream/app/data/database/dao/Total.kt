package com.studystream.app.data.database.dao

import com.studystream.app.data.database.base.BaseDao
import com.studystream.app.data.database.tables.*
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Device
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.model.Profile

// Reference to model class is needed to "Exposed" things
object AccountDao : BaseDao<Account>(AccountTable, Account::class)

object ProfileDao : BaseDao<Profile>(ProfileTable, Profile::class)

object DocumentDao : BaseDao<Document>(DocumentTable, Document::class)

object DocumentTypeDao : BaseDao<Document.Type>(DocumentTypeTable, Document.Type::class)

object DeviceDao : BaseDao<Device>(DeviceTable, Device::class)
