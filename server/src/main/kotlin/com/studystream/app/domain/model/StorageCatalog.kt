package com.studystream.app.domain.model

import com.studystream.app.domain.properties.feature.FileStorageProperties
import java.nio.file.Path
import java.nio.file.Paths

enum class StorageCatalog(
    internal val path: (FileStorageProperties) -> Path
) {
    Temp(
        path = {
            Paths.get(it.tempStorePath)
        }
    ),

    Regular(
        path = {
            Paths.get(it.regularStorePath)
        }
    ),
}
