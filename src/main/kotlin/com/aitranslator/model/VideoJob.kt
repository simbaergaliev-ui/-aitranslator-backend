package com.aitranslator.model

data class VideoJob(
    val id: String,
    val userId: String,
    val prompt: String,
    val status: String,
    val videoUrl: String? = null
)


