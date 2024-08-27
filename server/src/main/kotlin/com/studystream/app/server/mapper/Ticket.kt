package com.studystream.app.server.mapper

import com.studystream.app.domain.model.Ticket
import com.studystream.shared.payload.dto.TicketDto
import com.studystream.shared.payload.dto.TicketTypeDto

fun Ticket.toDto() = TicketDto(
    id = idValue,
    ownerId = owner.idValue,
    profileId = profile.idValue,
    typeId = type.idValue,
    activatedAt = activatedAt,
    closedAt = closedAt,
)

fun Ticket.Type.toDto() = TicketTypeDto(
    id = idValue,
    creatorId = creator.idValue,
    title = title,
    description = description,
    totalEvents = totalEvents,
    durationDays = durationDays,
)