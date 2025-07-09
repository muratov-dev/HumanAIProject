package me.yeahapps.talkingphoto.feature.videos.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.yeahapps.talkingphoto.feature.videos.data.model.VideoEntity

@Dao
interface VideosDao {

    @Query("select * from videos")
    fun getVideos(): Flow<List<VideoEntity>>

    @Query("select count(*) from videos")
    suspend fun getVideosCount(): Int

    @Query("select * from videos where id = :id")
    suspend fun getVideo(id: Long): VideoEntity?

    @Query("delete from videos where id = :id")
    suspend fun deleteVideo(id: Long)

    @Upsert
    suspend fun addVideo(model: VideoEntity): Long
}