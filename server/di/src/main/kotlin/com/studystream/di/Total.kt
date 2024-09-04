package com.studystream.di

import com.studystream.di.utils.singleMembersOf
import com.studystream.domain.properties.AppProperties
import org.koin.dsl.module
import org.slf4j.Logger

fun totalAppModules(properties: AppProperties, logger: Logger) = module {
    // Pass all properties from AppProperties to DI
    single { properties }
    singleMembersOf(properties)

    // Pass app logger to DI
    single { logger }

    includes(
        databaseModule,
        dataSourceModule,
        repositoryModule,
    )
}
