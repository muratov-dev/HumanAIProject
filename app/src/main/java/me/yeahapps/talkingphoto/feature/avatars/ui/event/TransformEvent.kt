package me.yeahapps.talkingphoto.feature.avatars.ui.event

import me.yeahapps.talkingphoto.feature.avatars.domain.ImageStyle

sealed interface TransformEvent {

    data class OnStyleSelected(val style: ImageStyle) : TransformEvent
}