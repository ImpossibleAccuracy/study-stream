plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = AppConfig.buildGroup("shared", "payload")
version = AppConfig.VERSION_NAME

dependencies {
    api(libs.kotlin.datetime)
    api(libs.kotlin.collections)
    api(libs.kotlin.serialization)
}
