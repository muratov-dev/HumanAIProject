package me.yeahapps.talkingphoto.feature.videos.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.feature.videos.domain.model.VideoModel
import me.yeahapps.talkingphoto.feature.videos.ui.action.VideosAction
import me.yeahapps.talkingphoto.feature.videos.ui.event.VideosEvent
import me.yeahapps.talkingphoto.feature.videos.ui.state.VideosState
import me.yeahapps.talkingphoto.feature.videos.ui.viewmodel.VideosViewModel

@Composable
fun VideosContainer(
    modifier: Modifier = Modifier, viewModel: VideosViewModel = hiltViewModel(), navigateToVideoInfo: (Long) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            is VideosAction.NavigateToVideoInfo -> navigateToVideoInfo(action.videoId)
            null -> {}
        }
    }
    VideosContent(modifier = modifier, state = state, onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun VideosContent(
    modifier: Modifier = Modifier, state: VideosState = VideosState(), onEvent: (VideosEvent) -> Unit = {}
) {
    Scaffold(modifier = modifier, topBar = {
        HumanAIPrimaryTopBar(title = stringResource(R.string.videos_headline))
    }) { innerPadding ->
        if (state.videosList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_videos_selected),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .alpha(0.5f)
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = stringResource(R.string.videos_empty_label),
                    color = HumanAITheme.colors.textPrimary,
                    style = HumanAITheme.typography.headlineBold
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    text = stringResource(R.string.videos_empty_description),
                    color = HumanAITheme.colors.textPrimary,
                    style = HumanAITheme.typography.titleMedium,
                    modifier = Modifier.alpha(0.5f)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.videosList) { video ->
                    UploadedPhotoCard(
                        videoInfo = video, onClick = { onEvent(VideosEvent.NavigateToVideoInfo(video.id.toLong())) })
                }
            }
        }
    }
}

@Composable
fun UploadedPhotoCard(modifier: Modifier = Modifier, videoInfo: VideoModel, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .aspectRatio(1f / 1.22f)
            .clip(RoundedCornerShape(32.dp))
            .background(color = Color(0xFF131238))
            .clickable(onClick = onClick), contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = videoInfo.imageUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .drawWithCache {
                    val gradient = Brush.verticalGradient(colors = listOf(Color(0x00040401), Color(0xCC040401)))
                    onDrawBehind {
                        drawRect(brush = gradient)
                    }
                })
        Row(
            modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = videoInfo.title, style = HumanAITheme.typography.labelMedium, modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.size(8.dp))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_play),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}