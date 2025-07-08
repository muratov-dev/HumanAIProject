package me.yeahapps.talkingphoto.feature.generating.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.feature.generating.ui.action.CreatingVideoAction
import me.yeahapps.talkingphoto.feature.generating.ui.state.CreatingVideoState
import me.yeahapps.talkingphoto.feature.generating.ui.viewmodel.CreatingVideoViewModel
import timber.log.Timber

@Serializable
data class CreatingVideoScreen(val audioScript: String? = null, val audioUri: String, val imageUri: String)

@Composable
fun CreatingVideoContainer(modifier: Modifier = Modifier, viewModel: CreatingVideoViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            is CreatingVideoAction.ShowVideoGeneratingError -> Timber.d("error")
            is CreatingVideoAction.NavigateToVideo -> Timber.d("success")
            null -> {}
        }
    }
    CreatingVideoContent(modifier = modifier, state = state)
}

@Composable
private fun CreatingVideoContent(modifier: Modifier = Modifier, state: CreatingVideoState = CreatingVideoState()) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}