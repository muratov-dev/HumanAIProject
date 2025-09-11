package me.yeahapps.liveface.core.data.network.model.get_video.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetVideoResponseDto(
    @SerialName("data") val videoData: VideoDataDto
)