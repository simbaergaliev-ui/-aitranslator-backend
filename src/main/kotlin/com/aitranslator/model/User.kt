package com.aitranslator.model

data class User(
    val id: String,
    val email: String,
    val passwordHash: String,
    val credits: Int = 0
)


