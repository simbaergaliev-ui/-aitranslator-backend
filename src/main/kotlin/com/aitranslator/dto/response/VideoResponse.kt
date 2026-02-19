package com.aitranslator.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val jobId: String,
    val status: String,
    val videoUrl: String? = null
)


