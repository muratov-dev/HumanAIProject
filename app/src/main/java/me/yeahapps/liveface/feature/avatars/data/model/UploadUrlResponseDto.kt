package me.yeahapps.liveface.feature.avatars.data.model


import kotlinx.serialization.Serializable

@Serializable
data class UploadUrlResponseDto(
    val body: UploadUrlResponseBodyDto,
    val message: String,
    val statusCode: Int
)