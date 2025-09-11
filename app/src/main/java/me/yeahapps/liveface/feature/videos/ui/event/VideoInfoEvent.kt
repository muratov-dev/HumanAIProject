package me.yeahapps.liveface.feature.videos.ui.event

sealed interface VideoInfoEvent {

    data object NavigateUp : VideoInfoEvent
    data object DeleteWork : VideoInfoEvent
    data object SaveToGallery : VideoInfoEvent
}