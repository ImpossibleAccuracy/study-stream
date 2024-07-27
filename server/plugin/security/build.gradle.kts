import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    alias(libs.plugins.kotlin.jvm)
}

archivesName = "plugin-security"
group = AppConfig.buildGroup("plugin", "security")
version = AppConfig.VERSION_NAME

dependencies {
    implementation(project(Modules.Server.Domain))
    implementation(project(Modules.Server.Shared.Security))

    implementation(libs.kotlin.coroutines)

    implementation(libs.ktor.core)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.authJwt)

    implementation(libs.logback)
}
