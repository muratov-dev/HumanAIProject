package me.yeahapps.liveface.feature.generating.ui.viewmodel

import android.os.CountDownTimer
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import me.yeahapps.liveface.core.ui.viewmodel.BaseViewModel
import me.yeahapps.liveface.feature.generating.domain.repository.GeneratingRepository
import me.yeahapps.liveface.feature.generating.ui.action.CreatingVideoAction
import me.yeahapps.liveface.feature.generating.ui.screen.CreatingVideoScreen
import me.yeahapps.liveface.feature.generating.ui.state.CreatingVideoState
import me.yeahapps.liveface.feature.videos.domain.model.VideoModel
import me.yeahapps.liveface.feature.videos.domain.repository.VideosRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CreatingVideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val generatingRepository: GeneratingRepository,
    private val videosRepository: VideosRepository,
) : BaseViewModel<CreatingVideoState, Any, CreatingVideoAction>(CreatingVideoState()) {

    private val args = savedStateHandle.toRoute<CreatingVideoScreen>()
    private var timer: CountDownTimer? = null

    override fun obtainEvent(viewEvent: Any) {}

    init {
        updateViewState { copy(imageUri = args.imageUri.toUri()) }
        startCreatingVideo()
    }


    private fun startCreatingVideo() = viewModelScoped {

        val imageUrl = generatingRepository.uploadImage(args.imageUri.toUri())
        if (imageUrl == null) {
            sendAction(CreatingVideoAction.ShowVideoGeneratingError)
            return@viewModelScoped
        }
        updateViewState { copy(isImageUploaded = true, isGeneratingVideo = true) }

        startProgressUpdateTimer()
        val audioUrl = if (args.audioUri != null) {
            generatingRepository.uploadAudio(args.audioUri.toUri())
        } else {
            val generatedAudioPath = args.audioScript?.let { generatingRepository.generateAudio(it) }
            if (generatedAudioPath == null) {
                sendAction(CreatingVideoAction.ShowVideoGeneratingError)
                return@viewModelScoped
            }
            val url = generatingRepository.uploadAudio(generatedAudioPath.toUri())
            url
        }
        if (audioUrl == null) {
            sendAction(CreatingVideoAction.ShowVideoGeneratingError)
            return@viewModelScoped
        }

        val animateImageId = generatingRepository.animateImage(imageUrl, audioUrl)
        if (animateImageId == null) {
            sendAction(CreatingVideoAction.ShowVideoGeneratingError)
            return@viewModelScoped
        }

        val videoPath = generatingRepository.getVideoUrl(animateImageId)
        if (videoPath == null) {
            sendAction(CreatingVideoAction.ShowVideoGeneratingError)
            return@viewModelScoped
        }
        timer?.cancel()
        timer = null
        val videoId = videosRepository.createVideo(
            VideoModel(
                title = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                imageUrl = imageUrl,
                videoPath = videoPath
            )
        )

        updateViewState { copy(progress = 1f, isGeneratingVideo = false, isGeneratingFinished = true) }
        delay(1000)
        sendAction(CreatingVideoAction.NavigateToVideo(videoId))
    }

    private fun startProgressUpdateTimer() {
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateViewState { copy(progress = (currentState.progress + 0.0165f)) }
            }

            override fun onFinish() {}
        }.start()
    }
}