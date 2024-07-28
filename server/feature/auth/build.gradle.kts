plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = AppConfig.buildGroup("feature", "auth")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))
    implementation(project(Modules.Server.Shared.Feature))
    implementation(project(Modules.Server.Shared.Security))
    implementation(project(Modules.Server.Shared.Socket))

    implementation(libs.kotlin.serialization)

    implementation(libs.ktor.core)
    implementation(libs.ktor.resources)
    implementation(libs.ktor.websockets)
    implementation(libs.ktor.auth)
    implementation(libs.koin.ktor)

    implementation(libs.logback)

    testImplementation(libs.kotlin.tests)
    testImplementation(libs.ktor.tests)
    testImplementation(libs.koin.tests)
}
