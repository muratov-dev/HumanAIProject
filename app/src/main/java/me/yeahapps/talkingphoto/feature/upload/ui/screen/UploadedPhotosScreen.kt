package me.yeahapps.talkingphoto.feature.upload.ui.screen

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.feature.upload.domain.model.UserImageModel
import me.yeahapps.talkingphoto.feature.upload.ui.action.UploadedPhotosAction
import me.yeahapps.talkingphoto.feature.upload.ui.component.cards.UploadedPhotoCard
import me.yeahapps.talkingphoto.feature.upload.ui.event.UploadedPhotosEvent
import me.yeahapps.talkingphoto.feature.upload.ui.state.UploadedPhotosState
import me.yeahapps.talkingphoto.feature.upload.ui.viewmodel.UploadedPhotosViewModel
import okio.Path.Companion.toPath
import java.io.File

@Composable
fun UploadedPhotosContainer(
    modifier: Modifier = Modifier,
    viewModel: UploadedPhotosViewModel = hiltViewModel(),
    navigateToUpload: () -> Unit,
    navigateToAddSound: (String) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            is UploadedPhotosAction.NavigateToPhotoUpload -> navigateToUpload()
            is UploadedPhotosAction.NavigateToSoundScreen -> navigateToAddSound(action.photoPath)
            null -> {}
        }
    }

    UploadedPhotosContent(
        modifier = modifier, state = state, onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun UploadedPhotosContent(
    modifier: Modifier = Modifier,
    state: UploadedPhotosState = UploadedPhotosState(),
    onEvent: (UploadedPhotosEvent) -> Unit = {}
) {
    val context = LocalContext.current
    Scaffold(modifier = modifier, topBar = {
        HumanAIPrimaryTopBar(title = stringResource(R.string.upload_headline))
    }) { innerPadding ->
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
                    UploadedPhotoCard { onEvent(UploadedPhotosEvent.NavigateToPhotoUpload) }
                }
                items(state.photos) { photo ->
                    UploadedPhotoCard(
                        context = context,
                        photo = photo,
                        onClick = { onEvent(UploadedPhotosEvent.NavigateToSoundScreen(it)) })
                }
            }
            HumanAIPrimaryButton(
                centerContent = stringResource(R.string.upload_main_button),
                onClick = { onEvent(UploadedPhotosEvent.NavigateToPhotoUpload) },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun UploadedPhotoCard(
    modifier: Modifier = Modifier, context: Context, photo: UserImageModel, onClick: (String) -> Unit
) {
    val bitmap = remember(photo.imagePath) {
        BitmapFactory.decodeFile(photo.imagePath)
    }

    val file = File(context.filesDir, photo.imagePath.toPath().name)
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

    Box(
        modifier = modifier
            .aspectRatio(1f / 1.22f)
            .clip(RoundedCornerShape(32.dp))
            .background(color = Color(0xFF131238))
            .clickable(onClick = { onClick(uri.toString()) }),
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}