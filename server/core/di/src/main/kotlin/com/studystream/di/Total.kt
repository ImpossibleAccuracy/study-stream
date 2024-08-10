package com.studystream.di

import com.studystream.domain.properties.AppProperties
import org.koin.dsl.module

fun totalAppModules(properties: AppProperties) = module {
    single { properties.token }
    single { properties.database }
    single { properties.feature.fileStorage }
    single { properties.feature.auth }

    includes(
        serviceModule,
        databaseModule,
    )
}