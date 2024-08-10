plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("shared", "feature")
version = AppConfig.VERSION_NAME

dependencies {
    api(project(Modules.Shared.Payload))
    api(project(Modules.Server.Domain))

    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.coroutines)

    implementation(libs.ktor.core)
    implementation(libs.ktor.resources)
}
