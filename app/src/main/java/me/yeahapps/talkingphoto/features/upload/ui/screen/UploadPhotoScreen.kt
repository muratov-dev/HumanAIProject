package me.yeahapps.talkingphoto.features.upload.ui.screen

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attafitamim.krop.core.crop.RectCropShape
import com.attafitamim.krop.core.crop.rememberImageCropper
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIButtonDefaults
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.core.ui.theme.robotoFamily
import me.yeahapps.talkingphoto.core.ui.utils.TempImage
import me.yeahapps.talkingphoto.core.ui.utils.createTempImageFile
import me.yeahapps.talkingphoto.features.crop.PhotoCropperScreen
import me.yeahapps.talkingphoto.features.crop.utils.rememberPickPhotoLauncher
import me.yeahapps.talkingphoto.features.crop.utils.rememberTakePhotoLauncher
import timber.log.Timber

@Serializable
object UploadPhotoScreen

@Composable
fun UploadPhotoContainer(modifier: Modifier = Modifier, navigateToCrop: (Uri) -> Unit) {
    UploadPhotoContent(modifier = modifier, navigateToCrop = navigateToCrop)
}

@Composable
private fun UploadPhotoContent(modifier: Modifier = Modifier, navigateToCrop: (Uri) -> Unit) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val imageCropper = rememberImageCropper()
    var tempImage: TempImage? by remember { mutableStateOf(null) }

    val goodPhotos = listOf(
        R.drawable.im_good_photo_1,
        R.drawable.im_good_photo_2,
        R.drawable.im_good_photo_3,
        R.drawable.im_good_photo_4,
    )
    val badPhotos = listOf(
        R.drawable.im_bad_photo_1,
        R.drawable.im_bad_photo_2,
        R.drawable.im_bad_photo_3,
        R.drawable.im_bad_photo_4,
    )
    val photoGuidelines = listOf("Only one person", "Close up shots", "Visible straight face", "Good light and quality")
    val photoDonts = listOf("Not facing the camera", "Many people")

    val imagePicker = rememberPickPhotoLauncher(imageCropper = imageCropper, onSuccess = {
        navigateToCrop(it)
        Timber.d(it.toString())
    }, onError = { Toast.makeText(context, "Error during image processing", Toast.LENGTH_SHORT).show() })

    val takePhoto =
        rememberTakePhotoLauncher(imageCropper = imageCropper, imageUriProvider = { tempImage?.uri }, onSuccess = {
            navigateToCrop(it)
            Timber.d(it.toString())
        }, onError = {
            Toast.makeText(context, "Error during image processing", Toast.LENGTH_SHORT).show()
            tempImage = null
        })

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted ->
            if (isGranted) {
                scope.launch {
                    tempImage = context.createTempImageFile().also {
                        if (it == null) Timber.d("No media selected")
                        else takePhoto.launch(it.uri)
                    }
                }
            }
        })

    Scaffold(modifier = modifier, topBar = {
        HumanAIPrimaryTopBar(title = stringResource(R.string.upload_headline_second), actions = {})
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.upload_good_title),
                    color = HumanAITheme.colors.textPrimary,
                    style = HumanAITheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
                Spacer(Modifier.size(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(goodPhotos) { photo ->
                        ExampleImageCard(imageRes = photo)
                    }
                }
                Spacer(Modifier.size(16.dp))
                FlowRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    photoGuidelines.forEach { requirement ->
                        PhotoGuidelinesText(
                            text = requirement,
                            iconRes = R.drawable.ic_check_circle,
                            iconTint = HumanAITheme.colors.success
                        )
                    }
                }
                Spacer(Modifier.size(32.dp))
                Text(
                    text = stringResource(R.string.upload_bad_title),
                    color = HumanAITheme.colors.textPrimary,
                    style = HumanAITheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
                Spacer(Modifier.size(16.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(badPhotos) { photo ->
                        ExampleImageCard(imageRes = photo)
                    }
                }
                Spacer(Modifier.size(16.dp))
                FlowRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    photoDonts.forEach { text ->
                        PhotoGuidelinesText(
                            text = text, iconRes = R.drawable.ic_cancel_circle, iconTint = HumanAITheme.colors.error
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                HumanAIPrimaryButton(
                    centerContent = stringResource(R.string.upload_take_photo_button),
                    onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) },
                    colors = HumanAIButtonDefaults.colors().copy(
                        containerColor = HumanAITheme.colors.backgroundSecondary,
                        contentColor = HumanAITheme.colors.buttonTextSecondary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(R.string.common_or).uppercase(),
                    color = HumanAITheme.colors.textPrimary,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                HumanAIPrimaryButton(
                    centerContent = stringResource(R.string.upload_gallery_button),
                    onClick = { imagePicker.launch(PickVisualMediaRequest(mediaType = ImageOnly)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    imageCropper.cropState?.let {
        it.apply {
            shape = RectCropShape
            aspectLock = false
        }
        PhotoCropperScreen(it)
    }
}

@Composable
private fun PhotoGuidelinesText(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int,
    iconTint: Color = HumanAITheme.colors.success
) {
    Row(
        modifier = modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(imageVector = ImageVector.vectorResource(iconRes), contentDescription = null, tint = iconTint)
        Text(text = text, color = HumanAITheme.colors.textPrimary, style = HumanAITheme.typography.labelMedium)
    }
}

@Composable
private fun ExampleImageCard(modifier: Modifier = Modifier, @DrawableRes imageRes: Int) {
    val shape = RoundedCornerShape(20.dp)
    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        modifier = modifier
            .size(88.dp)
            .border(2.dp, color = Color.White.copy(alpha = 0.4f), shape = shape)
            .clip(shape)
    )
}

fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/HumanAI")
    }

    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
    )
}