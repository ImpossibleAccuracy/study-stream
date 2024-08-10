plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = AppConfig.buildGroup("feature", "filestorage")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))
    implementation(project(Modules.Server.Shared.Feature))

    implementation(libs.kotlin.serialization)

    implementation(libs.ktor.core)
    implementation(libs.koin.ktor)

    testImplementation(libs.kotlin.tests)
    testImplementation(libs.ktor.tests)
    testImplementation(libs.koin.tests)
}
