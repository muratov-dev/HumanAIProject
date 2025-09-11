package me.yeahapps.liveface.feature.avatars.domain.model

data class UploadAvatarBodyModel(
    val avgResponseTimeInSec: Int,
    val maxRetriesAllowed: Int,
    val orderId: String,
    val status: String
)
