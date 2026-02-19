package com.aitranslator

import com.aitranslator.routes.videoRoutes
import com.aitranslator.service.LumaService
import com.aitranslator.service.VideoService
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.response.respondText

fun main() {
    embeddedServer(
        Netty,
        host = "0.0.0.0",
        port = 8080
    ) {
        module()
    }.start(wait = true)
}

fun Application.module() {

    // 🔥 Создаём сервис ОДИН РАЗ
    val videoService = VideoService(LumaService())

    install(CallLogging)

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
    }

    routing {

        get("/") {
            call.respondText("API is running")
        }

        // 🔥 Передаём существующий VideoService
        videoRoutes(videoService)
    }
}
