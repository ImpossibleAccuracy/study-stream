plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("di")
version = AppConfig.VERSION_NAME

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(Modules.Shared.Payload))
    implementation(project(Modules.Server.Domain))
    implementation(project(Modules.Server.Data))

    implementation(libs.koin.core)
    implementation(libs.koin.coroutines)
}
