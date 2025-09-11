package me.yeahapps.liveface.feature.upload.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.yeahapps.liveface.core.data.network.api.MainApiService
import me.yeahapps.liveface.core.data.network.model.save_user.SaveUserRequestDto
import me.yeahapps.liveface.feature.upload.data.local.UploadDao
import me.yeahapps.liveface.feature.upload.data.model.UploadEntity
import me.yeahapps.liveface.feature.upload.domain.model.UserImageModel
import me.yeahapps.liveface.feature.upload.domain.repository.UploadRepository
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val uploadDao: UploadDao,
    private val apiService: MainApiService,
    private val preferences: SharedPreferences
) : UploadRepository {

    override suspend fun saveUser() {
        val savedUserId = preferences.getString("userId", null)
        if (savedUserId != null) return
        val userId = UUID.randomUUID().toString().uppercase()
        apiService.saveUserId(SaveUserRequestDto(userId = userId))
        preferences.edit {
            putString("userId", userId)
            putBoolean("isFirstLaunch", false)
        }
    }

    override fun getUploadPhotos(): Flow<List<UserImageModel>> {
        return uploadDao.getUploads().map { list ->
            list.map { uploadEntity ->
                UserImageModel(imagePath = uploadEntity.imagePath, id = uploadEntity.id)
            }
        }
    }

    override suspend fun saveUploadPhoto(imagePath: Uri, shouldSaveToDb: Boolean): String? {
        val name = LocalDateTime.now().toString()
        val imagePath = savePhotoSilently(imagePath, "$name.jpg") ?: return null
        if (shouldSaveToDb) uploadDao.saveUpload(UploadEntity(imagePath = imagePath.absolutePath))

        return imagePath.absolutePath
    }

    private suspend fun savePhotoSilently(photoUri: Uri, fileName: String): File? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(photoUri) ?: return@withContext null

                val file = File(context.filesDir, fileName) // Внутреннее хранилище

                FileOutputStream(file).use { output ->
                    inputStream.use { input ->
                        input.copyTo(output)
                    }
                }

                file
            } catch (e: Exception) {
                Timber.e(e)
                null
            }
        }
    }
}