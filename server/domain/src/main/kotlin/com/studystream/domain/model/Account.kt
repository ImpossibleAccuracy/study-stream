package com.studystream.domain.model

import com.studystream.domain.security.Role

interface Account : BaseModel {
    var username: String
    var password: String

    val roles: Iterable<Role>
}
