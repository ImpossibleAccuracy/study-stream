package com.studystream.di

import com.studystream.data.database.dao.*
import com.studystream.data.database.setup.createDatabase
import org.koin.dsl.module

val databaseModule = module(createdAtStart = true) {
    single {
        createDatabase(
            properties = get(),
        )
    }

    single { AccountDao }
    single { RoleDao }
    single { PrivilegeDao }
    single { ProfileDao }
    single { DocumentDao }
    single { DocumentTypeDao }
    single { DeviceDao }
    single { DeviceTypeDao }
    single { TicketDao }
    single { TicketDao.TypeDao }
}