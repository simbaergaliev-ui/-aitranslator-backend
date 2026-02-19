package com.aitranslator.routes

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import kotlinx.serialization.Serializable

fun Route.healthRoutes() {

    get("/health") {
        call.respond(HealthResponse("OK"))
    }
}

@Serializable
data class HealthResponse(
    val status: String
)
