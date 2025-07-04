package me.yeahapps.talkingphoto.features.avatars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme

@Composable
fun AvatarsContainer(modifier: Modifier = Modifier, navigateToPhotoUpload: () -> Unit = {}) {
    AvatarsContent(modifier = modifier, navigateToPhotoUpload = navigateToPhotoUpload)
}

@Composable
private fun AvatarsContent(modifier: Modifier = Modifier, navigateToPhotoUpload: () -> Unit) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.im_avatars_bg),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxSize()
                    .drawWithCache {
                        val gradient = Brush.linearGradient(
                            colorStops = arrayOf(
                                0.0f to Color.Transparent, 0.5f to Color(0x6E03032A), 1.0f to Color(0xFF03032A)
                            ), start = Offset(0f, 0f), end = Offset(0f, size.height)
                        )
                        onDrawBehind {
                            drawRect(brush = gradient)
                        }
                    })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val annotatedText = buildAnnotatedString {
                    withStyle(SpanStyle(color = HumanAITheme.colors.textAccent)) {
                        append("Transform ")
                    }
                    withStyle(SpanStyle(color = HumanAITheme.colors.textPrimary)) {
                        append("Your\nPhotos with ")
                    }
                    withStyle(SpanStyle(color = HumanAITheme.colors.textAccent)) {
                        append("AI")
                    }
                }
                Text(text = annotatedText, style = HumanAITheme.typography.titleBold, textAlign = TextAlign.Center)
                Spacer(Modifier.size(16.dp))
                Text(text = stringResource(R.string.avatars_description), textAlign = TextAlign.Center)
                Spacer(Modifier.size(40.dp))
                HumanAIPrimaryButton(
                    centerContent = stringResource(R.string.avatars_create),
                    onClick = navigateToPhotoUpload,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}