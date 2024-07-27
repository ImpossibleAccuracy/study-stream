package com.solovocal.di

import com.solovocal.domain.properties.AppProperties
import org.koin.dsl.module

fun totalAppModules(properties: AppProperties) = module {
    includes(
        serviceModule(properties),
        databaseModule(properties),
    )
}