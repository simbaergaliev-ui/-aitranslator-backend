package com.aitranslator.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val email: String,
    val credits: Int
)


