plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = AppConfig.buildGroup("data")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))

    implementation(libs.ktor.authJwt)

    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.coroutines)

    api(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.datetime)
    implementation(libs.mysqlConnector)
    implementation(libs.hikariCP)
}
