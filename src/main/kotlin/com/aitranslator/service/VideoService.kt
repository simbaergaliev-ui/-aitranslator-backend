package com.aitranslator.service

import kotlinx.coroutines.runBlocking
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import com.aitranslator.routes.VideoStatusResponse

class VideoService(
    private val lumaService: LumaService
) {

    private val jobs = ConcurrentHashMap<String, VideoJob>()

    data class VideoJob(
        var status: String,
        var videoUrl: String? = null
    )

    fun createJob(
        prompt: String,
        duration: Int,
        audio: Boolean
    ): String {

        val jobId = UUID.randomUUID().toString()
        jobs[jobId] = VideoJob("processing")

        try {

            println("VIDEO JOB STARTED SYNC: $jobId")

            // ВАЖНО: теперь вызов НЕ в фоне
            val videoUrl = runBlocking {
                lumaService.generateVideo(prompt, duration, audio)
            }

            println("VIDEO JOB COMPLETED SYNC: $jobId")

            jobs[jobId] = VideoJob(
                status = "completed",
                videoUrl = videoUrl
            )

        } catch (e: Exception) {

            println("VIDEO JOB FAILED SYNC: $jobId")
            e.printStackTrace()

            jobs[jobId] = VideoJob("failed")
        }

        return jobId
    }

    fun getStatus(jobId: String): VideoStatusResponse {

        val job = jobs[jobId]
            ?: return VideoStatusResponse("not_found")

        return VideoStatusResponse(
            status = job.status,
            videoUrl = job.videoUrl
        )
    }
}
