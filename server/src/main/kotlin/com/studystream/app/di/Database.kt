package com.studystream.app.di

import com.studystream.app.data.database.dao.*
import com.studystream.app.data.database.setup.createDatabase
import org.koin.dsl.module

val databaseModule = module {
    single(createdAtStart = true) {
        createDatabase(
            properties = get(),
        )
    }

    single { AccountDao }
    single { ProfileDao }
    single { DocumentDao }
    single { DocumentTypeDao }
    single { DeviceDao }
    single { DeviceTypeDao }
}