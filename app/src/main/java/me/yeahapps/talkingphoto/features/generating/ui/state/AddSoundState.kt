package me.yeahapps.talkingphoto.features.generating.ui.state

import android.net.Uri

data class AddSoundState(
    val isRecording: Boolean = false,
    val isPlaying: Boolean = false,
    val audioDuration: Long = 0L,
    val userMessage: String = "",
    val selectedVoice: Int? = null,
    val userImageUri: Uri? = null,
    val userAudioUri: Uri? = null,
)
