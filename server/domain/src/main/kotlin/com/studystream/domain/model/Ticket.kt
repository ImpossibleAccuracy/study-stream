package com.studystream.domain.model

import kotlinx.datetime.LocalDateTime

interface Ticket : BaseModel {
    var owner: Account
    var profile: Profile
    var type: Type

    var activatedAt: LocalDateTime?
    var closedAt: LocalDateTime?

    interface Type : BaseModel {
        var creator: Account
        var title: String
        var description: String

        // TODO: merge into one object
        var totalEvents: Int?
        var durationDays: Int?
    }
}