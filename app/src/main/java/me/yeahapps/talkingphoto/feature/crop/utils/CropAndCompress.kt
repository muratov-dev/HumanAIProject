package me.yeahapps.talkingphoto.feature.crop.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.IntSize
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.crop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

suspend fun ImageCropper.cropAndCompress(
    inputUri: Uri, outputUri: Uri, context: Context, maxResultSize: IntSize = IntSize(2048, 2048)
): CropResult {
    val result = crop(
        uri = inputUri, context = context, maxResultSize = maxResultSize, cacheBeforeUse = false
    )
    return if (result is CropResult.Success) {
        try {
            withContext(Dispatchers.IO) {
                context.contentResolver.openOutputStream(outputUri)?.use {
                    val bitmap = result.bitmap.asAndroidBitmap()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    result
                } ?: CropError.SavingError
            }
        } catch (_: FileNotFoundException) {
            CropError.SavingError
        }
    } else result
}
