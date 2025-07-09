package me.yeahapps.talkingphoto.feature.videos.ui.state

import me.yeahapps.talkingphoto.feature.videos.domain.model.VideoModel

data class VideosState(
    val videosList: List<VideoModel> = emptyList()
)
