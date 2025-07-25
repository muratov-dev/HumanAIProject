package me.yeahapps.talkingphoto.feature.avatars.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UploadAvatarRequestDto(
    val imageUrl: String,
    val styleImageUrl: String = "",
    val textPrompt: String,
)
