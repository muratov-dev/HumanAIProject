package me.yeahapps.talkingphoto.feature.upload.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import me.yeahapps.talkingphoto.feature.upload.domain.model.UserImageModel

interface UploadRepository {

    suspend fun saveUser()

    fun getUploadPhotos(): Flow<List<UserImageModel>>
    suspend fun saveUploadPhoto(imagePath: Uri, shouldSaveToDb: Boolean = false): String?
}