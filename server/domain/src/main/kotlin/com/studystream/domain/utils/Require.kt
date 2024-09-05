package com.studystream.domain.utils

import com.studystream.domain.exception.ResourceNotFoundException
import com.studystream.domain.model.Account
import com.studystream.domain.model.Document
import com.studystream.domain.model.Profile
import com.studystream.domain.model.Ticket

fun Account?.require(): Account =
    require("Account not found")

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
