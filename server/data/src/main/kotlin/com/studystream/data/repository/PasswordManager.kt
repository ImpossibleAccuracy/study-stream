package com.studystream.data.repository

class PasswordManager {
    // TODO: add password encryption
    suspend fun match(source: String, encrypted: String): Boolean {
        return source == encrypted
    }

    suspend fun encrypt(password: String): String {
        return password
    }
}