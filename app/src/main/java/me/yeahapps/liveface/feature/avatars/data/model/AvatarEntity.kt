package me.yeahapps.liveface.feature.avatars.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatars")
data class AvatarEntity(
    val imagePath: String, @PrimaryKey(autoGenerate = true) val id: Int = 0
)
