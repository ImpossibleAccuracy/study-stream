package com.studystream.app.data.mapper

import com.studystream.app.data.database.dao.AccountDao
import com.studystream.app.domain.model.Account

fun AccountDao.toDomain() = Account(
    id = id.value,
    username = username,
    password = password,
)