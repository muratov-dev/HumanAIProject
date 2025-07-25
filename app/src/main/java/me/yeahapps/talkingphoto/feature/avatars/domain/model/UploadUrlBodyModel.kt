package me.yeahapps.talkingphoto.feature.avatars.domain.model


data class UploadUrlBodyModel(
    val imageUrl: String,
    val size: Int,
    val uploadImage: String
)