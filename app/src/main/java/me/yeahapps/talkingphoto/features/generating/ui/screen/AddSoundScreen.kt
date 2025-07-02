package me.yeahapps.talkingphoto.features.generating.ui.screen

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.icons.HumanAIIconButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAISecondaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.talkingphoto.core.ui.utils.formatMillisecondsToMmSs
import me.yeahapps.talkingphoto.core.ui.utils.toPainter
import me.yeahapps.talkingphoto.features.generating.ui.action.AddSoundAction
import me.yeahapps.talkingphoto.features.generating.ui.component.SoundControlsIconButton
import me.yeahapps.talkingphoto.features.generating.ui.event.AddSoundEvent
import me.yeahapps.talkingphoto.features.generating.ui.state.AddSoundState
import me.yeahapps.talkingphoto.features.generating.ui.viewmodel.AddSoundViewModel

@Serializable
data class AddSoundScreen(val imageUri: String)

@Composable
fun AddSoundContainer(
    modifier: Modifier = Modifier, viewModel: AddSoundViewModel = hiltViewModel(), navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = false
            prepare()
        }
    }


    val state by viewModel.viewState.collectAsStateWithLifecycle()
    state.userAudioUri?.let { exoPlayer.setMediaItem(MediaItem.fromUri(it)) }
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            AddSoundAction.PlaySound -> exoPlayer.play()
            AddSoundAction.PauseSound -> exoPlayer.pause()
            AddSoundAction.StartGenerating -> {}
            AddSoundAction.NavigateUp -> {}
            null -> {}
        }
    }
    AddSoundContent(
        context = context,
        modifier = modifier,
        state = state,
        onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun AddSoundContent(
    context: Context,
    modifier: Modifier = Modifier,
    state: AddSoundState = AddSoundState(),
    onEvent: (AddSoundEvent) -> Unit = {}
) {

    val activity = LocalActivity.current

    val options = listOf("Script", "Record")
    val icons = listOf(R.drawable.ic_script, R.drawable.ic_record)
    var selectedIndex by remember { mutableIntStateOf(0) }

    val permissionMessage = stringResource(R.string.add_sound_permission_denied)
    val micPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (!granted) {
            Toast.makeText(context, permissionMessage, Toast.LENGTH_SHORT).show()
            selectedIndex = 0
        }
    }

    LaunchedEffect(selectedIndex) {
        if (selectedIndex == 1) {
            micPermission.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    Scaffold(
        modifier = modifier, topBar = {
            HumanAISecondaryTopBar(
                title = stringResource(R.string.add_sound_title),
                navigationIcon = { HumanAIIconButton(icon = R.drawable.ic_arrow_back) })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(24.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEachIndexed { index, label ->
                    FilledIconToggleButton(
                        checked = selectedIndex == index,
                        onCheckedChange = { selectedIndex = index },
                        colors = IconButtonDefaults.filledIconToggleButtonColors().copy(
                            containerColor = Color.Transparent,
                            checkedContainerColor = Color.White,
                            contentColor = HumanAITheme.colors.textPrimary,
                            checkedContentColor = HumanAITheme.colors.backgroundPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = ImageVector.vectorResource(icons[index]), contentDescription = null)
                            Spacer(Modifier.size(6.dp))
                            Text(label)
                        }
                    }
                }
            }

            Spacer(Modifier.size(20.dp))
            state.userImageUri?.let {
                Image(
                    painter = it.toPainter(context),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(32.dp))
                )
            }

            if (selectedIndex == 0) {

            } else {
                SoundRecordControls(
                    audioDuration = state.audioDuration,
                    isPlayingButtonEnabled = state.userAudioUri != null,
                    isPlaying = state.isPlaying,
                    isRecording = state.isRecording,
                    onPlay = { onEvent(AddSoundEvent.PlaySound) },
                    onPause = { onEvent(AddSoundEvent.PauseSound) },
                    onRecord = { onEvent(AddSoundEvent.StartRecording) },
                    onStop = { onEvent(AddSoundEvent.StopRecording) },
                    onDone = { onEvent(AddSoundEvent.StartGenerating) })
            }
        }
    }
}

@Composable
private fun SoundRecordControls(
    modifier: Modifier = Modifier,
    audioDuration: Long = 0L,
    isPlayingButtonEnabled: Boolean = false,
    isPlaying: Boolean = false,
    isRecording: Boolean = false,
    onPlay: () -> Unit = {},
    onPause: () -> Unit = {},
    onRecord: () -> Unit = {},
    onStop: () -> Unit = {},
    onDone: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.size(24.dp))
        Text(text = audioDuration.formatMillisecondsToMmSs())
        Box(
            modifier = Modifier
                .padding(32.dp)
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                SoundControlsIconButton(
                    icon = if (isPlaying) R.drawable.ic_stop else R.drawable.ic_play,
                    enabled = isPlayingButtonEnabled,
                    onClick = { if (isPlaying) onPause() else onPlay() })
                SoundControlsIconButton(
                    icon = if (isRecording) R.drawable.ic_stop else R.drawable.ic_mic,
                    onClick = { if (isRecording) onStop() else onRecord() })
                SoundControlsIconButton(icon = R.drawable.ic_arrow_right, onClick = onDone)
            }
        }
    }
}