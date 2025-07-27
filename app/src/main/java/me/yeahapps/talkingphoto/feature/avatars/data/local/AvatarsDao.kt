package me.yeahapps.talkingphoto.feature.avatars.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.yeahapps.talkingphoto.feature.avatars.data.model.AvatarEntity

@Dao
interface AvatarsDao {

    @Query("select * from uploads")
    fun getAvatars(): Flow<List<AvatarEntity>>

    @Upsert
    suspend fun saveAvatar(upload: AvatarEntity)
}