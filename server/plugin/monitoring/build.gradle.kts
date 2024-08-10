plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("plugin", "monitoring")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(libs.ktor.core)
    implementation(libs.ktor.callLoging)
    implementation(libs.ktor.metrics)
}
