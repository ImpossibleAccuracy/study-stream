package com.studystream.app.server.utils

import com.studystream.app.domain.model.MultipartFile
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*

suspend fun ApplicationCall.receiveFile(
    name: String,
    defaultFileName: String = "unknown",
    defaultFileExtension: String = "raw",
): MultipartFile? {
    val multipart = receiveMultipart()

    var multipartFile: MultipartFile? = null

    multipart.forEachPart { part ->
        if (part is PartData.FileItem) {
            if (part.name == name) {
                multipartFile = MultipartFile(
                    bytes = part.streamProvider().readAllBytes(),
                    contentType = part.contentType?.contentType,
                    name = (part.originalFileName ?: defaultFileName).let {
                        if (it.contains(".")) it
                        else "$it.$defaultFileExtension"
                    },
                )
            }
        }

        part.dispose()
    }

    return multipartFile
}