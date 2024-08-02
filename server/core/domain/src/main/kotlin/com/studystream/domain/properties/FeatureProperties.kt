package com.studystream.domain.properties

data class FeatureProperties(
    val refreshToken: RefreshToken,
) {
    data class RefreshToken(
        val tokenRefreshThresholdMillis: Long,
    )
}
