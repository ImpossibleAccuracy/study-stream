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
    implementation(project(Modules.Server.Di))
    implementation(project(Modules.Server.Domain))

    implementation(project(Modules.Server.Plugin.Monitoring))
    implementation(project(Modules.Server.Plugin.Route))
    implementation(project(Modules.Server.Plugin.Security))
    implementation(project(Modules.Server.Plugin.Serialization))
    implementation(project(Modules.Server.Plugin.Sockets))

    implementation(libs.ktor.core)
    implementation(libs.ktor.netty)
    implementation(libs.ktor.config)

    implementation(libs.koin.logger)
    implementation(libs.koin.ktor)

    implementation(project(Modules.Server.Feature.Auth))
    implementation(project(Modules.Server.Feature.FileStorage))
}
