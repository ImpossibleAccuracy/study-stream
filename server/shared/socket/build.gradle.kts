plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("shared", "feature")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))
    implementation(project(Modules.Server.Shared.Security))

    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.coroutines)

    implementation(libs.ktor.core)
    implementation(libs.ktor.websockets)
    implementation(libs.ktor.resources)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.authJwt)

    implementation(libs.logback)
}
