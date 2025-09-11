package me.yeahapps.liveface.feature.settings.event

sealed interface SettingsEvent {

    data object GetVideosCount : SettingsEvent
    data object NavigateToMyVideos : SettingsEvent
    data object NavigateToSubscriptions : SettingsEvent

}