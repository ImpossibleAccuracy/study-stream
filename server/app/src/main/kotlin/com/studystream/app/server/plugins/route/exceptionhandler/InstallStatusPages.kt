package com.studystream.app.server.plugins.route.exceptionhandler

import com.studystream.domain.exception.ServiceException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.util.logging.*

fun Application.installExceptionHandler() {
    install(StatusPages) {
        exception<ServiceException> { call, cause ->
            call.respond(
                HttpStatusCode.fromValue(cause.status),
                ErrorResponse(
                    message = cause.localizedMessage ?: cause.message!!
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.application.log.error(cause)

            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    message = cause.localizedMessage ?: cause.message ?: "No error message"
                )
            )
        }
    }
}
