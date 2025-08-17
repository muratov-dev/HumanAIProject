package me.yeahapps.talkingphoto.feature.avatars.data.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.yeahapps.talkingphoto.core.data.network.api.AvatarApiService
import me.yeahapps.talkingphoto.feature.avatars.data.local.AvatarsDao
import me.yeahapps.talkingphoto.feature.avatars.data.model.AvatarEntity
import me.yeahapps.talkingphoto.feature.avatars.data.model.UploadAvatarRequestDto
import me.yeahapps.talkingphoto.feature.avatars.data.model.UploadUrlRequestDto
import me.yeahapps.talkingphoto.feature.avatars.data.model.toDomain
import me.yeahapps.talkingphoto.feature.avatars.domain.model.UploadAvatarBodyModel
import me.yeahapps.talkingphoto.feature.avatars.domain.model.UploadUrlBodyModel
import me.yeahapps.talkingphoto.feature.avatars.domain.repository.AvatarRepository
import me.yeahapps.talkingphoto.feature.upload.domain.model.UserImageModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

sealed class LightXError : Exception() {
    data class InvalidURL(override val message: String? = null) : LightXError()
    data class InvalidResponse(override val message: String? = null) : LightXError()
    data class ApiError(val statusCode: Int, override val message: String? = null) : LightXError()
    data class ImageConversionFailed(override val message: String? = null) : LightXError()
}

class AvatarRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val avatarApiService: AvatarApiService,
    private val avatarsDao: AvatarsDao
) : AvatarRepository {

    private var generatedAvatarUrl = ""
    override fun getAvatars(): Flow<List<UserImageModel>> {
        return avatarsDao.getAvatars().map { avatars ->
            avatars.map { UserImageModel(it.id, it.imagePath) }
        }
    }

    override suspend fun getUploadUrl(imageData: ByteArray): UploadUrlBodyModel? {
        val requestUrl = "https://api.lightxeditor.com/external/api/v2/uploadImageUrl"
        val requestBody = UploadUrlRequestDto(size = imageData.size)
        try {
            val response = avatarApiService.getUploadUrl(requestUrl, requestBody)
            if (response.isSuccessful) return response.body()?.body?.toDomain()
        } catch (ex: Exception) {
            Timber.e(ex)
            return null
        }
        return null
    }

    override suspend fun uploadImage(contentUri: Uri, uploadUrl: String): Result<Unit> {
        return try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(contentUri)

            if (inputStream == null) {
                Timber.e("InputStream is null for URI: $contentUri")
                return Result.failure(Exception("Cannot open input stream"))
            }

            val requestBody = inputStream.readBytes().toRequestBody("image/jpeg".toMediaTypeOrNull())

            val contentLength = requestBody.contentLength()
            Timber.d("Uploading image to S3: $uploadUrl (size=$contentLength bytes)")

            val response = avatarApiService.uploadImage(
                headers = mapOf("Content-Length" to contentLength.toString()),
                url = uploadUrl,
                requestData = requestBody
            )

            if (response.isSuccessful) {
                Timber.i("Image uploaded successfully to S3.")
                Result.success(Unit)
            } else {
                Timber.e("Image upload failed. Code: ${response.code()}")
                Result.failure(Exception("Error uploading image"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Exception during image upload")
            Result.failure(Exception("Error uploading image"))
        }
    }

    override suspend fun generateCartoon(imageUrl: String, prompt: String): UploadAvatarBodyModel? {
        val requestUrl = "https://api.lightxeditor.com/external/api/v1/cartoon"
        val requestBody = UploadAvatarRequestDto(imageUrl = imageUrl, textPrompt = prompt)
        try {
            val request = avatarApiService.generateCartoon(requestUrl, requestBody)
            return if (request.isSuccessful) {
                request.body()?.body?.toDomain()
            } else {
                null
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
        return null
    }

    override suspend fun waitForResult(orderId: String): String {
        var attempt = 0
        while (attempt < 5) {
            delay(5000)
            val (status, resultUrl) = checkOrderStatus(orderId)
            println("Статус: $status, Результат URL: ${resultUrl ?: "-"}")

            if (status == "active") {
                generatedAvatarUrl = resultUrl ?: ""
                return resultUrl ?: throw LightXError.InvalidResponse()
            }
            if (status == "failed") {
                throw LightXError.ApiError(500, "Ошибка генерации изображения")
            }
            attempt++
        }
        throw LightXError.ApiError(408, "Превышено время ожидания результата")
    }


    override suspend fun saveAvatar(avatarUrl: String?): Uri? {
        val avatarFile = savePhotoFromUrl(avatarUrl ?: return null, "avatar${LocalDateTime.now()}.jpg")
        avatarsDao.saveAvatar(AvatarEntity(avatarFile?.absolutePath ?: return null))
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", avatarFile)

    }

    override suspend fun saveAvatarToGallery(): Boolean {
        return saveImageToGallery(generatedAvatarUrl, "avatar${LocalDateTime.now()}.jpg")
    }

    suspend fun savePhotoFromUrl(url: String, fileName: String): File? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                val response = OkHttpClient().newCall(request).execute()

                if (!response.isSuccessful) return@withContext null

                val body = response.body ?: return@withContext null
                val inputStream = body.byteStream()

                val file = File(context.filesDir, fileName)

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

    suspend fun saveImageToGallery(url: String, displayName: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                val response = OkHttpClient().newCall(request).execute()

                if (!response.isSuccessful) {
                    Timber.e("Ошибка загрузки: ${response.code}")
                    return@withContext false
                }

                val body = response.body ?: return@withContext false
                val inputStream = body.byteStream()

                val resolver = context.contentResolver
                val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg") // или "image/png"
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }

                val uri = resolver.insert(imageCollection, values) ?: return@withContext false

                resolver.openOutputStream(uri)?.use { outputStream ->
                    inputStream.use { input ->
                        input.copyTo(outputStream)
                    }
                }

                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(uri, values, null, null)

                true
            } catch (e: Exception) {
                Timber.e(e, "Ошибка при загрузке и сохранении изображения")
                false
            }
        }
    }


    suspend fun checkOrderStatus(orderId: String): Pair<String, String?> {
        val requestBody = mapOf("orderId" to orderId)
        val response =
            avatarApiService.checkOrderStatus("https://api.lightxeditor.com/external/api/v1/order-status", requestBody)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Pair(body.body?.status ?: "", body.body?.output)
            }
        }
        return Pair("error", null)
    }

}