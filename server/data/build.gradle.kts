plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = AppConfig.buildGroup("data")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))

    implementation(libs.jwt)

    api(libs.exposed.core)
    api(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.datetime)
    implementation(libs.mysqlConnector)
    implementation(libs.hikariCP)
}
