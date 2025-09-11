package me.yeahapps.liveface.feature.avatars.domain.model


data class UploadUrlBodyModel(
    val imageUrl: String,
    val size: Int,
    val uploadImage: String
)