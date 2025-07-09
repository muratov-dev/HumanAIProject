package me.yeahapps.talkingphoto.feature.videos.domain.model

import me.yeahapps.talkingphoto.feature.videos.data.model.VideoEntity

data class VideoModel(
    val title: String,
    val imageUrl: String,
    val videoPath: String,
    val id: Int = 0
)

fun VideoModel.toEntity() = VideoEntity(title, imageUrl, videoPath, id)
