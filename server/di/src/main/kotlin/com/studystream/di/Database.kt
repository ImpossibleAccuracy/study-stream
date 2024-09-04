package com.studystream.di

import com.studystream.data.database.setup.createDatabase
import org.koin.dsl.module

val databaseModule = module(createdAtStart = true) {
    single {
        createDatabase(
            properties = get(),
        )
    }
}