package com.studystream.domain.properties

data class AppProperties(
    val token: TokenProperties,
    val database: DatabaseProperties,
    val featureProperties: FeatureProperties,
)
