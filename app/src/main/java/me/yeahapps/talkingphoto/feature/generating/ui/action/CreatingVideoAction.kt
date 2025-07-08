package me.yeahapps.talkingphoto.feature.generating.ui.action

sealed interface CreatingVideoAction {

    data object ShowVideoGeneratingError : CreatingVideoAction
    data class NavigateToVideo(val songName: String = "", val videoPath: String) : CreatingVideoAction
}