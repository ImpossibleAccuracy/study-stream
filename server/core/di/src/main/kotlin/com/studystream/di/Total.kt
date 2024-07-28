package com.studystream.di

import com.studystream.domain.properties.AppProperties
import org.koin.dsl.module

fun totalAppModules(properties: AppProperties) = module {
    includes(
        serviceModule(properties),
        databaseModule(properties),
    )
}