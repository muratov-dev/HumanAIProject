package me.yeahapps.talkingphoto.features.generating.ui.viewmodel

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.features.generating.data.media.AudioRecorder
import me.yeahapps.talkingphoto.features.generating.ui.action.AddSoundAction
import me.yeahapps.talkingphoto.features.generating.ui.event.AddSoundEvent
import me.yeahapps.talkingphoto.features.generating.ui.screen.AddSoundScreen
import me.yeahapps.talkingphoto.features.generating.ui.state.AddSoundState
import javax.inject.Inject

@HiltViewModel
class AddSoundViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val audioRecorder: AudioRecorder
) : BaseViewModel<AddSoundState, AddSoundEvent, AddSoundAction>(AddSoundState()) {

    val args = savedStateHandle.toRoute<AddSoundScreen>()
    private var audioRecordingJob: Job? = null

    override fun obtainEvent(viewEvent: AddSoundEvent) {
        when (viewEvent) {
            AddSoundEvent.StartRecording -> startRecording()
            AddSoundEvent.PlaySound -> {
                updateViewState { copy(isPlaying = true) }
                sendAction(AddSoundAction.PlaySound)
            }

            AddSoundEvent.PauseSound -> {
                updateViewState { copy(isPlaying = false) }
                sendAction(AddSoundAction.PauseSound)
            }

            AddSoundEvent.StopRecording -> stopRecording()
            AddSoundEvent.StartGenerating -> {}
            AddSoundEvent.NavigateUp -> cancelRecordingAndNavigateUp()

            is AddSoundEvent.OnMessageChanged -> updateMessage(viewEvent.message)
            AddSoundEvent.ClearMessageField -> updateMessage("")
        }
    }

    init {
        updateViewState { copy(userImageUri = args.imageUri.toUri()) }
    }

    private fun updateMessage(prompt: String) {
        updateViewState { copy(userMessage = prompt) }
    }

    private fun startRecording() {
        updateViewState { copy(isRecording = true, audioDuration = 0L) }
        audioRecorder.startRecording()
        audioRecordingJob = viewModelScoped {
            while (isActive) {
                val currentDuration = currentState.audioDuration + AUDIO_RECORDING_INTERVAL_MS
                if (currentDuration >= MAX_AUDIO_DURATION) {
                    stopRecording()
                    break
                }
                updateViewState { copy(audioDuration = currentDuration) }
                delay(AUDIO_RECORDING_INTERVAL_MS)
            }
        }
    }

    private fun stopRecording() {
        updateViewState { copy(isRecording = false) }
        audioRecorder.stopRecording()
        updateViewState { copy(userAudioUri = audioRecorder.outputFile?.toUri()) }
        audioRecordingJob?.cancel()
        audioRecordingJob = null
    }

    private fun cancelRecordingAndNavigateUp() {
        audioRecordingJob?.cancel()
        audioRecordingJob = null
        audioRecorder.cancelRecording()
        sendAction(AddSoundAction.NavigateUp)
    }

    companion object {
        private const val MAX_AUDIO_DURATION = 30_000L
        private const val AUDIO_RECORDING_INTERVAL_MS = 1000L
    }
}