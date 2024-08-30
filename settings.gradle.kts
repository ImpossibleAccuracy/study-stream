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
//include(":server:core:data")
//include(":server:core:domain")
//include(":server:core:di")
//
//include(":server:plugin:monitoring")
//include(":server:plugin:route")
//include(":server:plugin:security")
//include(":server:plugin:serialization")
//include(":server:plugin:sockets")
//
//include(":server:shared:feature")
//include(":server:shared:security")
//include(":server:shared:socket")
//
//include(":server:feature:auth")
//include(":server:feature:file-storage")
