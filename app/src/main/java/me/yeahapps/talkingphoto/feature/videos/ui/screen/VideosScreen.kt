package me.yeahapps.talkingphoto.feature.videos.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.feature.videos.domain.model.VideoModel
import me.yeahapps.talkingphoto.feature.videos.ui.state.VideosState
import me.yeahapps.talkingphoto.feature.videos.ui.viewmodel.VideosViewModel

@Composable
fun VideosContainer(modifier: Modifier = Modifier, viewModel: VideosViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    VideosContent(modifier = modifier, state = state)
}

@Composable
private fun VideosContent(
    modifier: Modifier = Modifier, state: VideosState = VideosState()
) {
    Scaffold(modifier = modifier, topBar = {
        HumanAIPrimaryTopBar(title = stringResource(R.string.videos_label))
    }) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            items(state.videosList) { video ->
                UploadedPhotoCard(videoInfo = video) { }
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
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(color = Color(0xFF7C8099), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                tint = HumanAITheme.colors.backgroundPrimary,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .matchParentSize()
            )
        }
    }
}