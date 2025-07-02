package me.yeahapps.talkingphoto.features.generating.ui.event

sealed interface AddSoundEvent {

    data object StartRecording: AddSoundEvent
    data object StopRecording: AddSoundEvent
    data object PlaySound: AddSoundEvent
    data object PauseSound: AddSoundEvent
    data object StartGenerating: AddSoundEvent
    data object NavigateUp: AddSoundEvent

}