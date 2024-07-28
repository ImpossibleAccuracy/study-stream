package com.studystream.data.mapper

import com.studystream.data.database.dao.AccountDao
import com.studystream.domain.model.Account

fun AccountDao.toDomain() = Account(
    id = id.value,
    username = username,
    password = password,
)