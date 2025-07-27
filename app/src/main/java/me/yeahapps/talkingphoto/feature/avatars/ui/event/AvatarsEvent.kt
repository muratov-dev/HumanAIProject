package me.yeahapps.talkingphoto.feature.avatars.ui.event

sealed interface AvatarsEvent {
    data object NavigateToPhotoUpload: AvatarsEvent
    data class NavigateToSoundScreen(val photoPath: String): AvatarsEvent
}