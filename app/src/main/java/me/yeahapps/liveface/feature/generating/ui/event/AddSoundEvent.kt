package me.yeahapps.liveface.feature.generating.ui.event

sealed interface AddSoundEvent {

    data class OnMessageChanged(val message: String) : AddSoundEvent
    data object ClearMessageField : AddSoundEvent

    data class OnVoiceSelect(val voiceId: Int) : AddSoundEvent

    data object StartRecording : AddSoundEvent
    data object StopRecording : AddSoundEvent
    data object PlaySound : AddSoundEvent
    data object PauseSound : AddSoundEvent
    data object StartGenerating : AddSoundEvent
    data object StartGeneratingWithTTS : AddSoundEvent
    data object NavigateUp : AddSoundEvent

}