plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("shared", "security")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))

    implementation(libs.kotlin.coroutines)

    implementation(libs.ktor.core)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.authJwt)
}
