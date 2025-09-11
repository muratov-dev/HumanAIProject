package me.yeahapps.liveface.feature.videos.ui.action

sealed interface VideoInfoAction {
    data object NavigateUp : VideoInfoAction
}