package com.solovocal.domain.service

import com.solovocal.domain.model.Account

interface TokenService {
    suspend fun generate(account: Account): String
}