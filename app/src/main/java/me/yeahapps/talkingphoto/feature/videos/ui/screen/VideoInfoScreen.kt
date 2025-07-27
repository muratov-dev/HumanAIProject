package me.yeahapps.talkingphoto.feature.videos.ui.screen

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.SubcomposeAsyncImage
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAISecondaryButton
import me.yeahapps.talkingphoto.core.ui.component.button.icons.HumanAIIconButton
import me.yeahapps.talkingphoto.core.ui.component.button.icons.HumanAIIconButtonDefaults
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAISecondaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.core.ui.utils.ManagePlayerLifecycle
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.core.ui.utils.shareFile
import me.yeahapps.talkingphoto.feature.videos.ui.action.VideoInfoAction
import me.yeahapps.talkingphoto.feature.videos.ui.event.VideoInfoEvent
import me.yeahapps.talkingphoto.feature.videos.ui.state.VideoInfoState
import me.yeahapps.talkingphoto.feature.videos.ui.viewmodel.VideoInfoViewModel
import java.io.File

@Serializable
data class VideoInfoScreen(val videoId: Long)

@Composable
fun VideoInfoContainer(
    modifier: Modifier = Modifier, viewModel: VideoInfoViewModel = hiltViewModel(), navigateUp: () -> Unit
) {

    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = true
        }
    }

    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            VideoInfoAction.NavigateUp -> navigateUp()
            null -> {}
        }
    }

    LaunchedEffect(state.videoPath) {
        state.videoPath?.let {
            exoPlayer.setMediaItem(MediaItem.fromUri(it.toUri()))
            exoPlayer.prepare()
        }
    }

    ManagePlayerLifecycle(exoPlayer)

    VideoInfoContent(
        exoPlayer = exoPlayer,
        modifier = modifier,
        state = state,
        onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@OptIn(UnstableApi::class)
@Composable
private fun VideoInfoContent(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    state: VideoInfoState = VideoInfoState(),
    onEvent: (VideoInfoEvent) -> Unit = {}
) {
    val context = LocalContext.current
    val hazeState = rememberHazeState(true)
    val hazeStyle = HazeStyle(blurRadius = 75.dp, tint = HazeTint(Color(0x0D000000)))

    var isDeleteDialogVisible by remember { mutableStateOf(false) }

    Scaffold(modifier = modifier, topBar = {
        HumanAISecondaryTopBar(title = stringResource(R.string.common_share), navigationIcon = {
            HumanAIIconButton(
                icon = R.drawable.ic_delete,
                colors = HumanAIIconButtonDefaults.colors().copy(contentColor = HumanAITheme.colors.error),
                onClick = { isDeleteDialogVisible = true })
        }, actions = {
            HumanAIIconButton(
                icon = R.drawable.ic_cancel_circle,
                modifier = Modifier.alpha(0.5f),
                onClick = { onEvent(VideoInfoEvent.NavigateUp) })
        })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp)) {
            Spacer(Modifier.size(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f / 1.39f, true)
                    .clip(RoundedCornerShape(32.dp))
            ) {
                state.imageUrl?.let {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black)
                    ) {
                        SubcomposeAsyncImage(
                            model = it,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .matchParentSize()
                                .hazeSource(hazeState)
                        )
                        Box(
                            Modifier
                                .matchParentSize()
                                .hazeEffect(hazeState, style = hazeStyle)
                        )
                    }
                } ?: Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black)
                )
                AndroidView(modifier = Modifier.matchParentSize(), factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                    }
                })
            }
            Spacer(Modifier.size(60.dp))
            Row {
                HumanAISecondaryButton(
                    centerContent = stringResource(R.string.common_save),
                    icon = R.drawable.ic_save,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onEvent(VideoInfoEvent.SaveToGallery)
                        Toast.makeText(context, "Saved to Gallery", Toast.LENGTH_SHORT).show()
                    })
                Spacer(Modifier.size(16.dp))
                HumanAISecondaryButton(
                    centerContent = stringResource(R.string.common_share),
                    icon = R.drawable.ic_send,
                    modifier = Modifier.weight(1f),
                    onClick = { state.videoPath?.let { shareFile(context, File(it), "video/mp4") } })
            }
            Spacer(Modifier.size(40.dp))
        }
    }

    if (isDeleteDialogVisible) {
        AlertDialog(title = {
            Text(
                text = "Delete video?",
                style = HumanAITheme.typography.titleMedium,
                color = HumanAITheme.colors.textPrimary
            )
        }, text = {
            Text(
                text = "Are you sure you want to delete this video?",
                style = HumanAITheme.typography.bodyMedium,
                color = HumanAITheme.colors.textPrimary
            )
        }, onDismissRequest = {
            isDeleteDialogVisible = false
        }, dismissButton = {
            Box(modifier = Modifier.clickable { isDeleteDialogVisible = false }) {
                Text(text = "Cancel", color = HumanAITheme.colors.textPrimary)
            }
        }, confirmButton = {
            Box(modifier = Modifier.clickable { onEvent(VideoInfoEvent.DeleteWork) }) {
                Text(text = "Delete", color = HumanAITheme.colors.textPrimary)
            }
        })
    }
}