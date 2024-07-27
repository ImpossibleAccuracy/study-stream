plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = AppConfig.buildGroup("di")
version = AppConfig.VERSION_NAME

dependencies {
    api(project(Modules.Server.Domain))
    implementation(project(Modules.Server.Data))

    implementation(libs.koin.core)
    implementation(libs.koin.coroutines)
    implementation(libs.koin.logger)
}
