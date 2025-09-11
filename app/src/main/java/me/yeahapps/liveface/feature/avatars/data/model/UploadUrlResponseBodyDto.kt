package me.yeahapps.liveface.feature.avatars.data.model


import kotlinx.serialization.Serializable
import me.yeahapps.liveface.feature.avatars.domain.model.UploadUrlBodyModel

@Serializable
data class UploadUrlResponseBodyDto(
    val imageUrl: String,
    val size: Int,
    val uploadImage: String
)

fun UploadUrlResponseBodyDto.toDomain() = UploadUrlBodyModel(imageUrl, size, uploadImage)