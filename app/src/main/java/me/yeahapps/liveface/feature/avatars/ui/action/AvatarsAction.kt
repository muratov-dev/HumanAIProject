package me.yeahapps.liveface.feature.avatars.ui.action

sealed interface AvatarsAction {

    data object NavigateToPhotoUpload: AvatarsAction
    data class NavigateToSoundScreen(val photoPath: String): AvatarsAction

}