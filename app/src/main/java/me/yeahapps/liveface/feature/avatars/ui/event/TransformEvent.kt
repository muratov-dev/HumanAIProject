package me.yeahapps.liveface.feature.avatars.ui.event

import me.yeahapps.liveface.feature.avatars.domain.ImageStyle

sealed interface TransformEvent {

    data object NavigateUp : TransformEvent
    data object SaveAvatar : TransformEvent
    data object SaveToGallery : TransformEvent
    data class OnStyleSelected(val style: ImageStyle) : TransformEvent
}