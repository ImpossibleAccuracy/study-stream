package com.studystream.app.data.database.dao

import com.studystream.app.data.database.tables.AccountTable
import com.studystream.app.data.database.tables.DocumentTable
import com.studystream.app.data.database.tables.DocumentTypeTable
import com.studystream.app.data.database.tables.ProfileTable
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.model.Profile

val AccountDao = buildDao<Account, AccountTable>(AccountTable)
val ProfileDao = buildDao<Profile, ProfileTable>(ProfileTable)
val DocumentDao = buildDao<Document, DocumentTable>(DocumentTable)
val DocumentTypeDao = buildDao<Document.Type, DocumentTypeTable>(DocumentTypeTable)
