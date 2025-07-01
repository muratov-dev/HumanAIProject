package me.yeahapps.talkingphoto.core.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.yeahapps.talkingphoto.BuildConfig
import java.io.File
import java.io.IOException
import kotlin.random.Random

private const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.provider"
private const val IMAGES_DIR = "images"

suspend fun Context.createTempImageFile(): TempImage? = withContext(Dispatchers.IO) {
    val imagesDir: File = cacheDir.resolve(IMAGES_DIR)
    imagesDir.mkdirs()

    val fileName = "JPEG_${Random.nextLong()}"
    val imageFile = try {
        File.createTempFile(fileName, null, imagesDir)
    } catch (e: IOException) {
        return@withContext null
    }

    val uri = FileProvider.getUriForFile(
        this@createTempImageFile, FILE_PROVIDER_AUTHORITY, imageFile
    )
    TempImage(
        uri = uri,
        file = imageFile
    )
}

suspend fun TempImage.delete(): Boolean = withContext(Dispatchers.IO) {
    file.delete()
}

data class TempImage(
    val uri: Uri,
    val file: File
)
