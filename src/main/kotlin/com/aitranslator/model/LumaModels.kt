package com.aitranslator.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateGenerationRequest(
    val prompt: String,
    val model: String,
    val resolution: String,
    val duration: String
)

@Serializable
data class GenerationResponse(
    val id: String,
    val state: String,
    val assets: Assets? = null,
    val failure_reason: String? = null
)

@Serializable
data class Assets(
    val video: String? = null
)


