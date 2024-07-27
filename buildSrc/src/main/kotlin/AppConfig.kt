@Suppress("MemberVisibilityCanBePrivate", "unused")
object AppConfig {
    const val BASE_PACKAGE = "com.solovocal"

    val APPLICATION_ID = buildGroup("app")
    const val VERSION_CODE = 1
    const val VERSION_NAME = "0.0.1"

    fun buildGroup(vararg string: String) =
        "$BASE_PACKAGE.${string.joinToString(".")}"
}
