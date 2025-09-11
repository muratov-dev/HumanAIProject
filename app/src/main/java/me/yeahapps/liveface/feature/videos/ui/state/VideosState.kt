package me.yeahapps.liveface.feature.videos.ui.state

import me.yeahapps.liveface.feature.videos.domain.model.VideoModel

data class VideosState(
    val videosList: List<VideoModel> = emptyList()
)
