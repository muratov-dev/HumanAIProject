package me.yeahapps.talkingphoto.feature.videos.ui.event

sealed interface VideosEvent {

    data class NavigateToVideoInfo(val videoId: Long) : VideosEvent
}