package me.yeahapps.talkingphoto.features.avatars.ui.event

import me.yeahapps.talkingphoto.features.avatars.domain.ImageStyle

sealed interface TransformEvent {

    data class OnStyleSelected(val style: ImageStyle) : TransformEvent
}