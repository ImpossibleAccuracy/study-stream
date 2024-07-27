plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = AppConfig.buildGroup("plugin", "serialization")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(libs.kotlin.serialization)

    implementation(libs.ktor.contentNegotiation)
    implementation(libs.ktor.serialization)
}
