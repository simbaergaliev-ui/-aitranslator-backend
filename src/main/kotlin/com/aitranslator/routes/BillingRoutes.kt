package com.aitranslator.routes

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Route.billingRoutes() {

    get("/billing/test") {
        call.respond("Billing route works")
    }
}


