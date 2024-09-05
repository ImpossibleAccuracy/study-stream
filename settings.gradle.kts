rootProject.name = "Study Stream"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}


include(":shared:payload")

include(":server:app")
include(":server:domain")
include(":server:data")
include(":server:di")