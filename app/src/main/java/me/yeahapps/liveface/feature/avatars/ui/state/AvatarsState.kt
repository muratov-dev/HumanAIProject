package me.yeahapps.liveface.feature.avatars.ui.state

import androidx.compose.runtime.Immutable
import me.yeahapps.liveface.feature.upload.domain.model.UserImageModel

@Immutable
data class AvatarsState(
    val avatars: List<UserImageModel> = emptyList()
)
