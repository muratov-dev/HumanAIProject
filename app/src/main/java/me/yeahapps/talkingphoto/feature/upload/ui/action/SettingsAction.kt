package me.yeahapps.talkingphoto.feature.upload.ui.action

sealed interface SettingsAction {

    data object NavigateToMyVideos : SettingsAction
    data object NavigateToSubscriptions : SettingsAction
}