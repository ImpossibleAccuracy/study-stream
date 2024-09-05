package com.studystream.app.properties

import com.studystream.domain.properties.AppProperties
import com.studystream.domain.properties.DatabaseProperties
import com.studystream.domain.properties.TokenProperties
import com.studystream.domain.properties.feature.AuthProperties
import com.studystream.domain.properties.feature.FileStorageProperties
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
    ),
)
