package com.aitranslator.routes

import com.aitranslator.service.VideoService
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

// 🔥 Теперь VideoService передаётся извне
fun Route.videoRoutes(videoService: VideoService) {

    post("/video/generate") {

        val request = call.receive<GenerateVideoRequest>()

        val jobId = videoService.createJob(
            request.prompt,
            request.duration,
            request.audio
        )

        call.respond(GenerateResponse(jobId))
    }

    get("/video/status/{jobId}") {

        val jobId = call.parameters["jobId"]
            ?: return@get call.respondText("Missing jobId")

        call.respond(videoService.getStatus(jobId))
    }
}

@Serializable
data class GenerateVideoRequest(
    val prompt: String,
    val duration: Int,
    val audio: Boolean
)

@Serializable
data class GenerateResponse(
    val jobId: String
)

@Serializable
data class VideoStatusResponse(
    val status: String,
    val videoUrl: String? = null
)
