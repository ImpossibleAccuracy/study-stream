plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = AppConfig.buildGroup("plugin", "route")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))

    implementation(libs.kotlin.serialization)

    implementation(libs.ktor.core)
    implementation(libs.ktor.resources)
    implementation(libs.ktor.statusPages)

    implementation(libs.logback)
}
