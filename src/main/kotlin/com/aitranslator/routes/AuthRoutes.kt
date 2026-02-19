package com.aitranslator.routes

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Route.authRoutes() {

    get("/auth/test") {
        call.respond("Auth route works")
    }
}


