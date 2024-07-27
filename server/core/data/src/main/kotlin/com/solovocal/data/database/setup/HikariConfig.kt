package com.solovocal.data.database.setup

import com.solovocal.domain.properties.DatabaseProperties
import com.zaxxer.hikari.HikariConfig

private const val MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver"

fun createHikariConfig(properties: DatabaseProperties) =
    HikariConfig().apply {
        jdbcUrl = properties.url
        driverClassName = MYSQL_DRIVER
        username = properties.user
        password = properties.password
        maximumPoolSize = properties.poolSize
        isReadOnly = false
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }
