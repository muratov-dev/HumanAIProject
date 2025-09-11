package me.yeahapps.liveface.core.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoiceSettingsDto(
    @SerialName("similarity_boost")
    val similarityBoost: Double = 0.2,
    val stability: Double = 0.5
)