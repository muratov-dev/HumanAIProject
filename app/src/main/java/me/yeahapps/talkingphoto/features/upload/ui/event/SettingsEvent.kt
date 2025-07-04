package me.yeahapps.talkingphoto.features.upload.ui.event

sealed interface SettingsEvent {

    data object NavigateToMyVideos: SettingsEvent
    data object NavigateToSubscriptions: SettingsEvent

}