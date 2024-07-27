package com.solovocal.di

import com.solovocal.data.database.setup.createDatabase
import com.solovocal.domain.properties.AppProperties
import org.koin.dsl.module

fun databaseModule(properties: AppProperties) = module {
    single(createdAtStart = true) { createDatabase(properties.database) }
}