package com.studystream.app.server.plugins.koin

import com.studystream.di.totalAppModules
import com.studystream.domain.properties.AppProperties
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(properties: AppProperties) {
    install(Koin) {
        slf4jLogger()

        modules(
            totalAppModules(properties, log),
        )
    }
}