package me.yeahapps.liveface.feature.avatars.data.model


import kotlinx.serialization.Serializable

@Serializable
data class OrderStatusResponseDto(
    val message: String,
    val statusCode: Int,
    val body: OrderStatusResponseBodyDto? = null,
    val description: String? = null,
    val timestamp: Long? = null,
    val responseHash: String? = null
)