plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("plugin", "sockets")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(libs.ktor.core)
    implementation(libs.ktor.websockets)
    implementation(libs.ktor.serialization)
}
