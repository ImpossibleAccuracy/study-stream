package com.studystream.domain.service

import com.studystream.domain.model.Account

interface TokenService {
    suspend fun generate(account: Account): String
}