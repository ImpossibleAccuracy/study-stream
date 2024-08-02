package com.studystream.app.properties

import com.studystream.domain.properties.AppProperties
import com.studystream.domain.properties.DatabaseProperties
import com.studystream.domain.properties.FeatureProperties
import com.studystream.domain.properties.TokenProperties
import io.ktor.server.application.*

fun ApplicationEnvironment.toAppProperties() = AppProperties(
    token = TokenProperties(
        secret = config.property("jwt.secret").getString(),
        issuer = config.property("jwt.issuer").getString(),
        audience = config.property("jwt.audience").getString(),
        realm = config.property("jwt.realm").getString(),
        claimName = config.property("jwt.claimName").getString(),
        ttl = config.property("jwt.ttl").getString().toLong(),
    ),
    database = DatabaseProperties(
        url = config.property("database.url").getString(),
        user = config.property("database.user").getString(),
        password = config.property("database.password").getString(),
        poolSize = config.property("database.poolSize").getString().toInt(),
    ),
    featureProperties = FeatureProperties(
        refreshToken = FeatureProperties.RefreshToken(
            tokenRefreshThresholdMillis = config
                .property("feature.refreshToken.tokenRefreshThreshold")
                .getString()
                .toLong()
        )
    ),
)
