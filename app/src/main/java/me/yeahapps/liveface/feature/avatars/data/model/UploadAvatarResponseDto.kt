package me.yeahapps.liveface.feature.avatars.data.model


import kotlinx.serialization.Serializable

@Serializable
data class UploadAvatarResponseDto(
    val body: UploadAvatarResponseBodyDto,
    val message: String,
    val statusCode: Int
)