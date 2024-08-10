package com.studystream.app.domain.properties

import com.studystream.app.domain.properties.feature.AuthProperties
import com.studystream.app.domain.properties.feature.FeatureProperties
import com.studystream.app.domain.properties.feature.FileStorageProperties
import io.ktor.server.application.*

data class AppProperties(
    val token: TokenProperties,
    val database: DatabaseProperties,
    val feature: FeatureProperties,
)

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
    feature = FeatureProperties(
        auth = AuthProperties(
            tokenRefreshThresholdMillis = config
                .property("feature.refreshToken.tokenRefreshThreshold")
                .getString()
                .toLong()
        ),
        fileStorage = FileStorageProperties(
            tempStorePath = config.property("store.temp").getString(),
            regularStorePath = config.property("store.regular").getString(),
            fileNamePattern = config.property("store.namingPattern").getString(),
            maxFilenameLength = config.property("store.maxFilenameLength").getString().toInt(),
        )
    ),
)
