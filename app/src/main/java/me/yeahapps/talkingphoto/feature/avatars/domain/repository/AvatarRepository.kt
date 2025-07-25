package me.yeahapps.talkingphoto.feature.avatars.domain.repository

import android.net.Uri
import me.yeahapps.talkingphoto.feature.avatars.domain.model.UploadAvatarBodyModel
import me.yeahapps.talkingphoto.feature.avatars.domain.model.UploadUrlBodyModel

interface AvatarRepository {

    suspend fun getUploadUrl(imageData: ByteArray): UploadUrlBodyModel?
    suspend fun uploadImage(contentUri: Uri, uploadUrl: String): Result<Unit>
    suspend fun generateCartoon(imageUrl: String, prompt: String): UploadAvatarBodyModel?

    suspend fun waitForResult(orderId: String): String
}