package com.studystream.di

import com.studystream.data.database.setup.createDatabase
import com.studystream.domain.properties.AppProperties
import org.koin.dsl.module

val databaseModule = module {
    single(createdAtStart = true) {
        createDatabase(
            properties = get<AppProperties>().database,
        )
    }
}