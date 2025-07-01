package me.yeahapps.talkingphoto.features.upload.ui.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIButtonDefaults
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.core.ui.theme.robotoFamily

@Serializable
object UploadPhotoScreen

@Composable
fun UploadPhotoContainer(modifier: Modifier = Modifier) {
    UploadPhotoContent(modifier = modifier)
}

@Composable
private fun UploadPhotoContent(modifier: Modifier = Modifier) {

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
                            text = text,
                            iconRes = R.drawable.ic_cancel_circle,
                            iconTint = HumanAITheme.colors.error
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
                    onClick = {},
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
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
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