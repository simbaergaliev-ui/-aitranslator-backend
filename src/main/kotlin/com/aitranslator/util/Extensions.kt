package com.aitranslator.util

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.http.*

suspend fun ApplicationCall.respondSuccess(message: String) {
    respond(
        HttpStatusCode.OK,
        mapOf("message" to message)
    )
}

suspend fun ApplicationCall.respondError(message: String) {
    respond(
        HttpStatusCode.BadRequest,
        mapOf("error" to message)
    )
}


