package me.yeahapps.talkingphoto.feature.upload.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import me.yeahapps.talkingphoto.core.data.network.api.MainApiService
import me.yeahapps.talkingphoto.core.data.network.model.save_user.SaveUserRequestDto
import me.yeahapps.talkingphoto.feature.upload.domain.repository.UploadRepository
import java.util.UUID
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
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
}