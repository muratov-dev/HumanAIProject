package me.yeahapps.talkingphoto.feature.avatars.data.model


import kotlinx.serialization.Serializable

@Serializable
data class OrderStatusResponseBodyDto(
    val orderId: String,
    val output: String?,
    val status: String?
)