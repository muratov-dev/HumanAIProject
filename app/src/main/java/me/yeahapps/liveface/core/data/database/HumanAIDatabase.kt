package me.yeahapps.liveface.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.yeahapps.liveface.core.data.database.converter.IntListTypeConverter
import me.yeahapps.liveface.core.data.database.converter.StringListTypeConverter
import me.yeahapps.liveface.feature.avatars.data.local.AvatarsDao
import me.yeahapps.liveface.feature.avatars.data.model.AvatarEntity
import me.yeahapps.liveface.feature.upload.data.local.UploadDao
import me.yeahapps.liveface.feature.upload.data.model.UploadEntity
import me.yeahapps.liveface.feature.videos.data.local.VideosDao
import me.yeahapps.liveface.feature.videos.data.model.VideoEntity

@Database(
    entities = [VideoEntity::class, UploadEntity::class, AvatarEntity::class], version = 3, exportSchema = false
)
@TypeConverters(
    IntListTypeConverter::class,
    StringListTypeConverter::class,
)
abstract class HumanAIDatabase : RoomDatabase() {
    abstract fun videosDao(): VideosDao
    abstract fun uploadDao(): UploadDao
    abstract fun avatarsDao(): AvatarsDao
}