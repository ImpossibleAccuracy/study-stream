package com.solovocal.app.properties

import com.solovocal.domain.properties.AppProperties
import com.solovocal.domain.properties.DatabaseProperties
import com.solovocal.domain.properties.TokenProperties
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
)
