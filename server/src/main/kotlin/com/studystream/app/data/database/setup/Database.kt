package com.studystream.app.data.database.setup

import com.studystream.app.domain.properties.DatabaseProperties
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

fun createDatabase(properties: DatabaseProperties): Database {
    val config = createHikariConfig(properties)

    val dataSource = HikariDataSource(config)

    return Database.connect(
        datasource = dataSource,
    )
}
