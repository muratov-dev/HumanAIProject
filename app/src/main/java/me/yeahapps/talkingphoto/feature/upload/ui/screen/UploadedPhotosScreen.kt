package me.yeahapps.talkingphoto.feature.upload.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.feature.upload.ui.action.UploadedPhotosAction
import me.yeahapps.talkingphoto.feature.upload.ui.component.cards.UploadedPhotoCard
import me.yeahapps.talkingphoto.feature.upload.ui.event.UploadedPhotosEvent
import me.yeahapps.talkingphoto.feature.upload.ui.state.UploadedPhotosState
import me.yeahapps.talkingphoto.feature.upload.ui.viewmodel.UploadedPhotosViewModel

@Composable
fun UploadedPhotosContainer(
    modifier: Modifier = Modifier, viewModel: UploadedPhotosViewModel = hiltViewModel(), navigateToUpload: () -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            is UploadedPhotosAction.NavigateToPhotoUpload -> navigateToUpload()
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
    Scaffold(modifier = modifier, topBar = {
        HumanAIPrimaryTopBar(title = stringResource(R.string.upload_headline))
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp, start = 16.dp, end = 16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            ) {
                item {
                    UploadedPhotoCard { onEvent(UploadedPhotosEvent.NavigateToPhotoUpload) }
                }
            }
            HumanAIPrimaryButton(
                centerContent = stringResource(R.string.upload_main_button),
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}