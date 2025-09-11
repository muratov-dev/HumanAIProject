package me.yeahapps.liveface.core.data.network.model.animate_image.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimateImageResponseDto(
    @SerialName("data") val animateImageData: AnimateImageDataDto
)