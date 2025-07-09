package me.yeahapps.talkingphoto.feature.videos.ui.action

sealed interface VideosAction {

    data class NavigateToVideoInfo(val videoId: Long) : VideosAction
}