package me.yeahapps.talkingphoto.feature.settings.crop.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.ImageCropper
import kotlinx.coroutines.launch
import me.yeahapps.talkingphoto.core.ui.utils.createTempImageFile
import me.yeahapps.talkingphoto.core.ui.utils.delete
import timber.log.Timber

@Composable
fun rememberPickPhotoLauncher(
    imageCropper: ImageCropper, onSuccess: (Uri) -> Unit, onError: () -> Unit
): ActivityResultLauncher<PickVisualMediaRequest> {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(), onResult = { imageUri ->
            imageUri ?: return@rememberLauncherForActivityResult

            coroutineScope.launch {
                val outputImage = context.createTempImageFile()
                if (outputImage == null) {
                    onError()
                    Timber.d("Failed to create temporary file.")
                    return@launch
                }

                val result = imageCropper.cropAndCompress(imageUri, outputImage.uri, context)
                if (result is CropResult.Success) {
                    onSuccess(outputImage.uri)
                } else if (result is CropError) {
                    onError()
                    outputImage.delete()
                    Timber.d("Failed to get image. $result")
                }
            }
        })
}