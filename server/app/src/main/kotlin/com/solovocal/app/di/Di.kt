package com.solovocal.app.di

import com.solovocal.di.totalAppModules
import com.solovocal.domain.properties.AppProperties
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(properties: AppProperties) {
    install(Koin) {
        slf4jLogger()

        modules(totalAppModules(properties))
    }
}