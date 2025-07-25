package me.yeahapps.talkingphoto.feature.avatars.data.model


import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.feature.avatars.domain.model.UploadAvatarBodyModel

@Serializable
data class UploadAvatarResponseBodyDto(
    val avgResponseTimeInSec: Int, val maxRetriesAllowed: Int, val orderId: String, val status: String
)

fun UploadAvatarResponseBodyDto.toDomain() =
    UploadAvatarBodyModel(avgResponseTimeInSec, maxRetriesAllowed, orderId, status)