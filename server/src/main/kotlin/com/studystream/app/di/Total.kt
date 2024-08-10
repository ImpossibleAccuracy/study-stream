package com.studystream.app.di

import com.studystream.app.domain.properties.AppProperties
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import kotlin.reflect.full.memberProperties

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
    AppProperties::class.memberProperties.forEach { prop ->
        single { prop.get(properties) }
    }

    includes(
        serviceModule,
        databaseModule,
    )
}

private inline val Application.applicationModule: Module
    get() = module {
        single { log }
    }
