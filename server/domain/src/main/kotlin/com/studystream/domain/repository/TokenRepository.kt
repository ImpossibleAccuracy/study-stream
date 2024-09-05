package com.studystream.domain.repository

import com.studystream.domain.model.Account

interface TokenRepository {
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