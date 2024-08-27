package com.studystream.app.domain.security

enum class Permission(
    val privilegeName: String,
) {
    READ_ALL_DEVICES("READ_ALL_DEVICES"),
    READ_ACCOUNTS("READ_ACCOUNTS"),
    CREATE_ACCOUNTS("WRITE_ACCOUNTS"),
    UPDATE_ANY_ACCOUNTS("WRITE_ACCOUNTS"),
    DELETE_ACCOUNTS("DELETE_ACCOUNTS"),
}