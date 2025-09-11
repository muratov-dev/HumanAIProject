package me.yeahapps.liveface.feature.upload.ui.state

import me.yeahapps.liveface.feature.upload.domain.model.UserImageModel

data class UploadedPhotosState(
    val photos: List<UserImageModel> = emptyList()
)
