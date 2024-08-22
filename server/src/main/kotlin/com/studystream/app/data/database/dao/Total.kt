package com.studystream.app.data.database.dao

import com.studystream.app.data.database.tables.*
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Device
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.model.Profile
import org.jetbrains.exposed.dao.IntEntityClass

// Reference to model class is needed to "Exposed" things
object AccountDao : IntEntityClass<Account>(AccountTable, Account::class.java)

object ProfileDao : IntEntityClass<Profile>(ProfileTable, Profile::class.java)

object DocumentDao : IntEntityClass<Document>(DocumentTable, Document::class.java)

object DocumentTypeDao : IntEntityClass<Document.Type>(DocumentTypeTable, Document.Type::class.java)

object DeviceDao : IntEntityClass<Device>(DeviceTable, Device::class.java)
