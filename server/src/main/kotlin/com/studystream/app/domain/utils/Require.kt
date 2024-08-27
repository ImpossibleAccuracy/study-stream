package com.studystream.app.domain.utils

import com.studystream.app.domain.exception.ResourceNotFoundException
import com.studystream.app.domain.model.Account
import com.studystream.app.domain.model.Document
import com.studystream.app.domain.model.Profile
import com.studystream.app.domain.model.Ticket

fun Account?.require(): Account =
    require("account not found")

fun Profile?.require(): Profile =
    require("Profile not found")

fun Ticket?.require(): Ticket =
    require("Ticket not found")

fun Ticket.Type?.require(): Ticket.Type =
    require("Ticket type not found")

fun Document.Type?.require() =
    require("Document type not found")

private fun <T> T?.require(message: String): T {
    return this ?: throw ResourceNotFoundException(message)
}
