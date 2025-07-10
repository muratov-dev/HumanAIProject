package me.yeahapps.talkingphoto.feature.videos.ui.action

sealed interface VideoInfoAction {
    data object NavigateUp : VideoInfoAction
}