package com.studystream.feature.filestorage.domain.model

data class MultipartFile(
    val name: String,
    val contentType: String?,
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultipartFile

        if (name != other.name) return false
        if (contentType != other.contentType) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (contentType?.hashCode() ?: 0)
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}
