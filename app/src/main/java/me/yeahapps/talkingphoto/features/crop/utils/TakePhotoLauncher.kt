package me.yeahapps.talkingphoto.features.crop.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.ImageCropper
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun rememberTakePhotoLauncher(
    imageCropper: ImageCropper, imageUriProvider: () -> Uri?, onSuccess: (Uri) -> Unit, onError: () -> Unit
): ActivityResultLauncher<Uri> {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(), onResult = { success ->
            if (!success) return@rememberLauncherForActivityResult
            val imageUri = imageUriProvider() ?: return@rememberLauncherForActivityResult

            coroutineScope.launch {
                val result = imageCropper.cropAndCompress(imageUri, imageUri, context)

                if (result is CropResult.Success) {
                    onSuccess(imageUri)
                } else if (result is CropError) {
                    onError()
                    Timber.d("Failed to take photo. $result")
                }
            }
        })
}