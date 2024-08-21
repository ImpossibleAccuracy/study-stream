package com.studystream.app.server.plugins.monitoring

import com.codahale.metrics.Slf4jReporter
import io.ktor.server.application.*
import io.ktor.server.metrics.dropwizard.*
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.Level
import java.util.concurrent.TimeUnit

fun Application.configureMonitoring() {
    if (environment.developmentMode) {
        install(CallLogging) {
            level = Level.DEBUG
        }
    }

    if (!environment.developmentMode) {
        install(DropwizardMetrics) {
            Slf4jReporter.forRegistry(registry)
                .outputTo(this@configureMonitoring.log)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build()
                .start(10, TimeUnit.SECONDS)
        }
    }
}
