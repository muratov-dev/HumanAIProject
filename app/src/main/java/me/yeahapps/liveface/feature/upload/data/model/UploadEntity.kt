package me.yeahapps.liveface.feature.upload.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uploads")
data class UploadEntity(
    val imagePath: String, @PrimaryKey(autoGenerate = true) val id: Int = 0
)
