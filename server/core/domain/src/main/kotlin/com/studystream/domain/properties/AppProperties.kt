package com.studystream.domain.properties

import com.studystream.domain.properties.feature.FeatureProperties

data class AppProperties(
    val token: TokenProperties,
    val database: DatabaseProperties,
    val feature: FeatureProperties,
)
