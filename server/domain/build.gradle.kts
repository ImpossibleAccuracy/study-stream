plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("domain")
version = AppConfig.VERSION_NAME

dependencies {
    api(libs.kotlin.datetime)
    api(libs.kotlin.collections)
    api(libs.kotlin.coroutines)
    api(libs.kotlin.serialization)
}
