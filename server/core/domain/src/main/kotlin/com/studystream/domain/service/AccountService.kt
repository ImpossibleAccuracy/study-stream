package com.studystream.domain.service

import com.studystream.domain.model.Account

interface AccountService {
    suspend fun findUser(username: String): Account?
}