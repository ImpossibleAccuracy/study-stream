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

dependencies {
    implementation(project(Modules.Shared.Payload))
    implementation(project(Modules.Server.Domain))
    implementation(project(Modules.Server.Data))
    implementation(project(Modules.Server.Di))

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
    implementation(libs.ktor.callLoging)

    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    implementation(libs.log4j.core)
    implementation(libs.log4j.impl)
}
