package com.studystream.data.mapper

import com.studystream.data.database.dao.DocumentDao
import com.studystream.data.database.dao.DocumentTypeDao
import com.studystream.domain.model.Document

fun DocumentTypeDao.toDomain() = Document.Type(
    id = id.value,
    title = title,
    mimeType = mimeType,
)

fun DocumentDao.toDomain() = Document(
    id = id.value,
    title = title,
    hash = hash,
    path = path,
    type = type.toDomain()
)