package me.yeahapps.liveface.feature.avatars.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import me.yeahapps.liveface.feature.avatars.domain.model.UploadAvatarBodyModel
import me.yeahapps.liveface.feature.avatars.domain.model.UploadUrlBodyModel
import me.yeahapps.liveface.feature.upload.domain.model.UserImageModel

interface AvatarRepository {

    fun getAvatars(): Flow<List<UserImageModel>>

    suspend fun getUploadUrl(imageData: ByteArray): UploadUrlBodyModel?
    suspend fun uploadImage(contentUri: Uri, uploadUrl: String): Result<Unit>
    suspend fun generateCartoon(imageUrl: String, prompt: String): UploadAvatarBodyModel?

    suspend fun waitForResult(orderId: String): String

    suspend fun saveAvatar(avatarUrl: String?): Uri?
    suspend fun saveAvatarToGallery(): Boolean
}