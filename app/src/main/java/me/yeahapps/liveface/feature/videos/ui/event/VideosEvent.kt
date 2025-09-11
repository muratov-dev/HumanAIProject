package me.yeahapps.liveface.feature.videos.ui.event

sealed interface VideosEvent {

    data class NavigateToVideoInfo(val videoId: Long) : VideosEvent
}