package com.studystream.di

import com.studystream.di.utils.singlePropertiesOf
import com.studystream.domain.properties.AppProperties
import org.koin.dsl.module
import org.slf4j.Logger

fun totalAppModules(properties: AppProperties, logger: Logger) = module {
    // Pass all properties from AppProperties to DI
    single { properties }
    singlePropertiesOf(properties)

    // Logger
    single { logger }

    includes(
        serviceModule,
        databaseModule,
    )
}
