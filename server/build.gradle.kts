plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

group = AppConfig.APPLICATION_ID
version = AppConfig.VERSION_NAME

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Modules.Shared.Payload))
//    implementation(project(Modules.Server.Domain))
//
//    implementation(project(Modules.Server.Plugin.Monitoring))
//    implementation(project(Modules.Server.Plugin.Route))
//    implementation(project(Modules.Server.Plugin.Security))
//    implementation(project(Modules.Server.Plugin.Serialization))
//    implementation(project(Modules.Server.Plugin.Sockets))

    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization)

    implementation(libs.ktor.core)
    implementation(libs.ktor.netty)
    implementation(libs.ktor.config)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.contentNegotiation)
    implementation(libs.ktor.resources)
    implementation(libs.ktor.statusPages)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.authJwt)
    implementation(libs.ktor.websockets)
    implementation(libs.ktor.metrics)
    implementation(libs.ktor.callLoging)

    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.coroutines)
    implementation(libs.koin.logger)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.datetime)
    implementation(libs.mysqlConnector)
    implementation(libs.hikariCP)

    implementation(libs.log4j.core)
    implementation(libs.log4j.impl)

    testImplementation(libs.kotlin.tests)

//    implementation(project(Modules.Server.Feature.Auth))
//    implementation(project(Modules.Server.Feature.FileStorage))
}
