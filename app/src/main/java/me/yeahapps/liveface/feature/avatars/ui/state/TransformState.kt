package me.yeahapps.liveface.feature.avatars.ui.state

import android.net.Uri
import me.yeahapps.liveface.feature.avatars.domain.ImageStyle

data class TransformState(
    val hasSubscription: Boolean = false,
    val isLoading: Boolean = false,
    val canContinue: Boolean = false,
    val selectedStyle: ImageStyle? = null,
    val userImageUri: Uri? = null,
    val avatarUrl: String? = null
)
