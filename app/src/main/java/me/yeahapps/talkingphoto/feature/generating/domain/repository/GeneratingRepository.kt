package me.yeahapps.talkingphoto.feature.generating.domain.repository

import android.net.Uri

interface GeneratingRepository {
    suspend fun uploadImage(filePath: Uri): String?
    suspend fun uploadAudio(filePath: Uri): String?

    suspend fun generateAudio(text: String, voiceId: String = "g5CIjZEefAph4nQFvHAz"): String?

    suspend fun animateImage(imageUrl: String, audioUrl: String): String?

    suspend fun getVideoUrl(token: String): String?
}