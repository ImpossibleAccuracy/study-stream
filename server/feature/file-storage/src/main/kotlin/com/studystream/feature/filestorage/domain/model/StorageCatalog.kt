package com.studystream.feature.filestorage.domain.model

import com.studystream.domain.properties.feature.FileStorageProperties
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
