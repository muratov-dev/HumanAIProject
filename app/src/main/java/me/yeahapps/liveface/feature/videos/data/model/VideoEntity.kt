package me.yeahapps.liveface.feature.videos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.yeahapps.liveface.feature.videos.domain.model.VideoModel

@Entity(tableName = "videos")
data class VideoEntity(
    val title: String, val imageUrl: String, val videoPath: String, @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun VideoEntity.toDomain() = VideoModel(title, imageUrl, videoPath, id)
