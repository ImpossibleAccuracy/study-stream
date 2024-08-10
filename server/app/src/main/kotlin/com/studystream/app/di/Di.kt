package com.studystream.app.di

import com.studystream.di.totalAppModules
import com.studystream.domain.properties.AppProperties
import com.studystream.feature.filestorage.di.fileStorageModule
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
            fileStorageModule,
            applicationModule
        )
    }
}

private inline val Application.applicationModule: Module
    inline get() = module {
        single { log }
    }
