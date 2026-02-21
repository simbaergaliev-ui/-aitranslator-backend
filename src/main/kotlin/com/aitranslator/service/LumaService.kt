package com.aitranslator.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.coroutines.delay

class LumaService {

    private val apiKey = System.getenv("LUMA_API_KEY")
        ?: throw IllegalStateException("LUMA_API_KEY not set")

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
        engine {
            requestTimeout = 60_000
        }
    }

    suspend fun generateVideo(
        prompt: String,
        duration: Int,
        audio: Boolean
    ): String {

        println("######## LUMA METHOD CALLED ########")

        val durationString = when (duration) {
            5 -> "5s"
            9 -> "9s"
            10 -> "10s"
            else -> {
                println("Invalid duration requested: $duration. Forcing to 5s")
                "5s"
            }
        }

        println("LUMA: Sending create request...")

        val createResponseHttp: HttpResponse =
            client.post("https://api.lumalabs.ai/dream-machine/v1/generations") {
                header(HttpHeaders.Authorization, "Bearer $apiKey")
                contentType(ContentType.Application.Json)
                setBody(
                    CreateGenerationRequest(
                        prompt = prompt,
                        model = "ray-flash-2",
                        resolution = "720p",
                        duration = durationString
                    )
                )
            }

        val createStatus = createResponseHttp.status
        val createRaw = createResponseHttp.bodyAsText()

        println("LUMA CREATE HTTP STATUS: $createStatus")
        println("LUMA CREATE RAW RESPONSE: $createRaw")

        if (!createStatus.isSuccess()) {
            throw IllegalStateException("Luma create failed: $createStatus")
        }

        val createResponse =
            json.decodeFromString<GenerationResponse>(createRaw)

        val generationId = createResponse.id
        println("LUMA GENERATION ID: $generationId")

        var attempts = 0
        val maxAttempts = 60

        while (attempts < maxAttempts) {

            delay(3000)
            attempts++

            val statusHttp =
                client.get("https://api.lumalabs.ai/dream-machine/v1/generations/$generationId") {
                    header(HttpHeaders.Authorization, "Bearer $apiKey")
                }

            val statusCode = statusHttp.status
            val statusRaw = statusHttp.bodyAsText()

            println("LUMA STATUS HTTP: $statusCode")
            println("LUMA STATUS RAW RESPONSE: $statusRaw")

            if (!statusCode.isSuccess()) {
                throw IllegalStateException("Luma status failed: $statusCode")
            }

            val status =
                json.decodeFromString<GenerationResponse>(statusRaw)

            when (status.state) {

                "completed" -> {
                    val videoUrl = status.assets?.video
                        ?: throw IllegalStateException("Video URL missing")
                    println("LUMA SUCCESS URL: $videoUrl")
                    return videoUrl
                }

                "failed" -> {
                    throw IllegalStateException(
                        "Luma generation failed: ${status.failure_reason}"
                    )
                }

                "processing", "queued" -> {
                    println("LUMA still processing...")
                }

                else -> {
                    println("LUMA unknown state: ${status.state}")
                }
            }
        }

        throw IllegalStateException("Luma timeout exceeded")
    }

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
        val failure_reason: String? = null,
        val assets: Assets? = null
    )

    @Serializable
    data class Assets(
        val video: String? = null
    )
}
