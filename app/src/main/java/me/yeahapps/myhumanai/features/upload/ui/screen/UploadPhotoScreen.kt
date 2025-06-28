package me.yeahapps.myhumanai.features.upload.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import me.yeahapps.myhumanai.R
import me.yeahapps.myhumanai.core.ui.component.button.filled.HumanAIButtonDefaults
import me.yeahapps.myhumanai.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.myhumanai.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.myhumanai.core.ui.theme.HumanAITheme
import me.yeahapps.myhumanai.core.ui.theme.robotoFamily

@Serializable
object UploadPhotoScreen

@Composable
fun UploadPhotoContainer(modifier: Modifier = Modifier) {
    UploadPhotoContent(modifier = modifier)
}

@Composable
private fun UploadPhotoContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier, topBar = {
        HumanAIPrimaryTopBar(title = stringResource(R.string.upload_headline_second), actions = {})
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 40.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { }
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
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