package me.yeahapps.talkingphoto.features.upload.ui.action

sealed interface SettingsAction {

    data object NavigateToMyVideos : SettingsAction
    data object NavigateToSubscriptions : SettingsAction
}