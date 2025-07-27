package me.yeahapps.talkingphoto.feature.avatars.ui.state

import android.net.Uri
import me.yeahapps.talkingphoto.feature.avatars.domain.ImageStyle

data class TransformState(
    val isLoading: Boolean = false,
    val canContinue: Boolean = false,
    val selectedStyle: ImageStyle? = null,
    val userImageUri: Uri? = null,
    val avatarUrl: String? = null
)
