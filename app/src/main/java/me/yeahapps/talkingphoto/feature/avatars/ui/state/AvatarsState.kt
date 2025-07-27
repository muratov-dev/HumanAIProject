package me.yeahapps.talkingphoto.feature.avatars.ui.state

import androidx.compose.runtime.Immutable
import me.yeahapps.talkingphoto.feature.upload.domain.model.UserImageModel

@Immutable
data class AvatarsState(
    val avatars: List<UserImageModel> = emptyList()
)
