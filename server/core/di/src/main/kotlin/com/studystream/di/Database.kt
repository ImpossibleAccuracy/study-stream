package com.studystream.di

import com.studystream.data.database.setup.createDatabase
import com.studystream.domain.properties.AppProperties
import org.koin.dsl.module

fun databaseModule(properties: AppProperties) = module {
    single(createdAtStart = true) { createDatabase(properties.database) }
}