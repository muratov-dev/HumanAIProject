package me.yeahapps.liveface.core.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextToSpeechRequestDto(
    @SerialName("model_id")
    val modelId: String = "eleven_multilingual_v2",
    val text: String,
    @SerialName("voice_settings")
    val voiceSettings: VoiceSettingsDto = VoiceSettingsDto()
)