package me.yeahapps.talkingphoto.feature.upload.ui.event

sealed interface SettingsEvent {

    data object NavigateToMyVideos: SettingsEvent
    data object NavigateToSubscriptions: SettingsEvent

}