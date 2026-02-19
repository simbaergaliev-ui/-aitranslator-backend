package com.aitranslator.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class VideoStatusResponse(
    val status: String,
    val videoUrl: String? = null
)


