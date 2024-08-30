package com.studystream.domain.model

interface Device : BaseModel {
    var owner: Account
    var name: String?
    var token: String
    var type: Type

    enum class Type {
        ANDROID,
        IOS,
        WINDOWS,
        WEB,
        NOT_SPECIFIED;

        fun nullIfNotSpecified(): Type? = when (this) {
            NOT_SPECIFIED -> null
            else -> this
        }
    }
}
