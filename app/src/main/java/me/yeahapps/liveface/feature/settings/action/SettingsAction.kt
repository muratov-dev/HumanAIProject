package me.yeahapps.liveface.feature.settings.action

sealed interface SettingsAction {

    data object NavigateToMyVideos : SettingsAction
    data object NavigateToSubscriptions : SettingsAction
}