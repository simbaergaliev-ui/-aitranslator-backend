package com.aitranslator.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class GenerateVideoRequest(
    val prompt: String,
    val duration: Int = 7,        // длительность видео
    val audio: Boolean = true     // генерировать ли музыку
)
