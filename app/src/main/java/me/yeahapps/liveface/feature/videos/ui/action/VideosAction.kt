package me.yeahapps.liveface.feature.videos.ui.action

sealed interface VideosAction {

    data class NavigateToVideoInfo(val videoId: Long) : VideosAction
}