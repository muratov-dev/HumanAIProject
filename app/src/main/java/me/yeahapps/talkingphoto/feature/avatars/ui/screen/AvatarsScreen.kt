package me.yeahapps.talkingphoto.feature.avatars.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.feature.avatars.ui.action.AvatarsAction
import me.yeahapps.talkingphoto.feature.avatars.ui.event.AvatarsEvent
import me.yeahapps.talkingphoto.feature.avatars.ui.state.AvatarsState
import me.yeahapps.talkingphoto.feature.avatars.ui.viewmodel.AvatarsViewModel
import me.yeahapps.talkingphoto.feature.upload.ui.component.cards.UploadedPhotoCard
import me.yeahapps.talkingphoto.feature.upload.ui.screen.UploadedPhotoCard

@Composable
fun AvatarsContainer(
    modifier: Modifier = Modifier,
    viewModel: AvatarsViewModel = hiltViewModel(),
    navigateToPhotoUpload: () -> Unit = {},
    navigateToAddSound: (String) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            AvatarsAction.NavigateToPhotoUpload -> navigateToPhotoUpload()
            is AvatarsAction.NavigateToSoundScreen -> navigateToAddSound(action.photoPath)
            null -> {}
        }
    }
    AvatarsContent(
        modifier = modifier,
        state = state,
        onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun AvatarsContent(
    modifier: Modifier = Modifier, state: AvatarsState = AvatarsState(), onEvent: (AvatarsEvent) -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(modifier = modifier, topBar = {
        if (state.avatars.isNotEmpty()) HumanAIPrimaryTopBar(title = stringResource(R.string.avatars_headline))
    }) { innerPadding ->
        if (state.avatars.isEmpty()) {
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
                        onClick = { onEvent(AvatarsEvent.NavigateToPhotoUpload) },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp, start = 16.dp, end = 16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)
                ) {
                    item {
                        UploadedPhotoCard { onEvent(AvatarsEvent.NavigateToPhotoUpload) }
                    }
                    items(state.avatars) { photo ->
                        UploadedPhotoCard(
                            context = context,
                            photo = photo,
                            onClick = { onEvent(AvatarsEvent.NavigateToSoundScreen(it)) })
                    }
                }
                HumanAIPrimaryButton(
                    centerContent = stringResource(R.string.upload_main_button),
                    onClick = { onEvent(AvatarsEvent.NavigateToPhotoUpload) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}