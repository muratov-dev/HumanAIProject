package me.yeahapps.liveface.feature.videos.domain.repository

import kotlinx.coroutines.flow.Flow
import me.yeahapps.liveface.feature.videos.domain.model.VideoModel

interface VideosRepository {

    suspend fun getVideoInfo(id: Long): VideoModel
    suspend fun getVideos(): Flow<List<VideoModel>>

    suspend fun createVideo(model: VideoModel): Long
    suspend fun deleteVideo(id: Long)

    suspend fun getVideosCount(): Int
}