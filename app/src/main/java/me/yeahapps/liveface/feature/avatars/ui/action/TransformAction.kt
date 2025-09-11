package me.yeahapps.liveface.feature.avatars.ui.action

import android.net.Uri

sealed interface TransformAction {

    data object NavigateUp: TransformAction
    data class NavigateToGenerating(val avatarUri: Uri): TransformAction
}