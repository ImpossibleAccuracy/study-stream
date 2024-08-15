package com.studystream.app.di

import com.studystream.app.di.utils.singlePropertiesOf
import com.studystream.app.domain.properties.AppProperties
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(properties: AppProperties) {
    install(Koin) {
        slf4jLogger()

        modules(
            totalAppModules(properties),
            applicationModule,
        )
    }
}

fun totalAppModules(properties: AppProperties) = module {
    // Pass all properties from AppProperties to DI
    single { properties }
    singlePropertiesOf(properties)

    includes(
        serviceModule,
        databaseModule,
    )
}

private inline val Application.applicationModule: Module
    get() = module {
        single { log }
    }
