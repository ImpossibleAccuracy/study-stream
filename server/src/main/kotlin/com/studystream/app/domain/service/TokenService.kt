package com.studystream.app.domain.service

import com.studystream.app.domain.model.Account

interface TokenService {
    suspend fun refreshToken(
        token: String,
        refreshThresholdMillis: Long,
    ): Result<RefreshedToken>

    suspend fun generate(account: Account): Result<String>

    data class RefreshedToken(
        val account: Account,
        val token: String,
    )
}