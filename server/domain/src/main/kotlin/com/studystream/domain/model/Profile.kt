package com.studystream.domain.model

import kotlinx.datetime.LocalDate

interface Profile : BaseModel {
    var name: String
    var surname: String
    var patronymic: String?
    var birthday: LocalDate
    var account: Account
    var avatar: Document?
}
