package com.studystream.app.server.feature.ticket

import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Id
import com.studystream.app.domain.service.TicketService
import com.studystream.app.server.feature.BaseRoutes
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Resource("/ticket")
class Tickets(
    @Suppress("unused") val parent: BaseRoutes = BaseRoutes()
) {
    @Serializable
    @Resource("")
    class List(
        @SerialName("owner_id")
        val ownerId: Id? = null,
        @SerialName("profile_id")
        val profileId: Id? = null,
        @SerialName("type_id")
        val typeId: Id? = null,
        @Suppress("unused") val parent: Tickets = Tickets()
    )

    @Serializable
    @Resource("/{id}")
    class TicketId(
        @SerialName("id")
        val id: Id,
        @Suppress("unused") val parent: Types = Types()
    ) {
        suspend fun verify(ticketService: TicketService) {
            if (!ticketService.existsTicket(id)) {
                throw ResourceNotFoundException("Ticket not found")
            }
        }
    }

    @Resource("/type")
    class Types(
        @Suppress("unused") val parent: Tickets = Tickets()
    ) {
        @Resource("")
        class List(
            @Suppress("unused") val parent: Types = Types()
        )

        @Serializable
        @Resource("/{id}")
        class TypeId(
            @SerialName("id")
            val id: Id,
            @Suppress("unused") val parent: Types = Types()
        ) {
            suspend fun verify(ticketService: TicketService) {
                if (!ticketService.existsTicketType(id)) {
                    throw ResourceNotFoundException("Ticket type not found")
                }
            }
        }
    }
}