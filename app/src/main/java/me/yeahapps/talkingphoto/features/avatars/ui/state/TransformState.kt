package me.yeahapps.talkingphoto.features.avatars.ui.state

import android.net.Uri
import me.yeahapps.talkingphoto.features.avatars.domain.ImageStyle

data class TransformState(
    val canContinue: Boolean = false,
    val selectedStyle: ImageStyle? = null,
    val userImageUri: Uri? = null
)
