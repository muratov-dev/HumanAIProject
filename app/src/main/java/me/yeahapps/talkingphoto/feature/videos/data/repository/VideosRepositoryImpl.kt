package me.yeahapps.talkingphoto.feature.videos.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.yeahapps.talkingphoto.feature.videos.data.local.VideosDao
import me.yeahapps.talkingphoto.feature.videos.data.model.toDomain
import me.yeahapps.talkingphoto.feature.videos.domain.model.VideoModel
import me.yeahapps.talkingphoto.feature.videos.domain.model.toEntity
import me.yeahapps.talkingphoto.feature.videos.domain.repository.VideosRepository
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(
    private val dao: VideosDao
) : VideosRepository {

    override suspend fun getVideoInfo(id: Long): VideoModel {
        return dao.getVideo(id)?.toDomain() ?: throw NoSuchElementException("Video with id $id not found")
    }

    override suspend fun getVideos(): Flow<List<VideoModel>> {
        return dao.getVideos().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun createVideo(model: VideoModel): Long {
        return dao.addVideo(model.toEntity())
    }

    override suspend fun deleteVideo(id: Long) {
        dao.deleteVideo(id)
    }

    override suspend fun getVideosCount(): Int {
        return dao.getVideosCount()
    }
}