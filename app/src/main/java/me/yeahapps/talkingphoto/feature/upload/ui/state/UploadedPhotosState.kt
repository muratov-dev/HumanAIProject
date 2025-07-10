package me.yeahapps.talkingphoto.feature.upload.ui.state

import me.yeahapps.talkingphoto.feature.upload.domain.model.UserImageModel

data class UploadedPhotosState(
    val photos: List<UserImageModel> = emptyList()
)
