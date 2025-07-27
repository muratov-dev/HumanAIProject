package me.yeahapps.talkingphoto.feature.avatars.ui.action

import android.net.Uri

sealed interface TransformAction {

    data class NavigateToGenerating(val avatarUri: Uri): TransformAction
}