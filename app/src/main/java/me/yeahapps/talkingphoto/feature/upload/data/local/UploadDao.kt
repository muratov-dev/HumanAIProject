package me.yeahapps.talkingphoto.feature.upload.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.yeahapps.talkingphoto.feature.upload.data.model.UploadEntity

@Dao
interface UploadDao {

    @Query("select * from uploads")
    fun getUploads(): Flow<List<UploadEntity>>

    @Upsert
    suspend fun saveUpload(upload: UploadEntity)
}