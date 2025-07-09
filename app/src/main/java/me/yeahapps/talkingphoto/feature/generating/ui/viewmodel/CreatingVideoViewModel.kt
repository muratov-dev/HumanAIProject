package me.yeahapps.talkingphoto.feature.generating.ui.viewmodel

import android.os.CountDownTimer
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.generating.domain.repository.GeneratingRepository
import me.yeahapps.talkingphoto.feature.generating.ui.action.CreatingVideoAction
import me.yeahapps.talkingphoto.feature.generating.ui.screen.CreatingVideoScreen
import me.yeahapps.talkingphoto.feature.generating.ui.state.CreatingVideoState
import me.yeahapps.talkingphoto.feature.videos.domain.model.VideoModel
import me.yeahapps.talkingphoto.feature.videos.domain.repository.VideosRepository
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
        fun sendError() = {
            sendAction(CreatingVideoAction.ShowVideoGeneratingError)
            return@viewModelScoped
        }

        val imageUrl = generatingRepository.uploadImage(args.imageUri.toUri())
        if (imageUrl == null) sendError()
        updateViewState { copy(isImageUploaded = true, isGeneratingVideo = true) }

        startProgressUpdateTimer()
        val audioUrl = generatingRepository.uploadAudio(args.audioUri.toUri())
        if (audioUrl == null) sendError()

        val animateImageId = generatingRepository.animateImage(imageUrl!!, audioUrl!!)
        if (animateImageId == null) sendError()

        val videoPath = generatingRepository.getVideoUrl(animateImageId!!)
        if (videoPath == null) sendError()
        timer?.cancel()
        timer = null
        val videoId = videosRepository.createVideo(
            VideoModel(
                title = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                imageUrl = imageUrl,
                videoPath = videoPath!!
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