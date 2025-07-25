package me.yeahapps.talkingphoto.feature.avatars.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UploadUrlRequestDto(
    val uploadType: String = "imageUrl",
    val size: Int,
    val contentType: String = "image/jpeg"
)
