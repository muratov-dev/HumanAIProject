package me.yeahapps.talkingphoto.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.yeahapps.talkingphoto.core.data.database.converter.IntListTypeConverter
import me.yeahapps.talkingphoto.core.data.database.converter.StringListTypeConverter
import me.yeahapps.talkingphoto.feature.videos.data.local.VideosDao
import me.yeahapps.talkingphoto.feature.videos.data.model.VideoEntity

@Database(
    entities = [VideoEntity::class], version = 1, exportSchema = false
)
@TypeConverters(
    IntListTypeConverter::class,
    StringListTypeConverter::class,
)
abstract class HumanAIDatabase : RoomDatabase() {
    abstract fun videosDao(): VideosDao
}