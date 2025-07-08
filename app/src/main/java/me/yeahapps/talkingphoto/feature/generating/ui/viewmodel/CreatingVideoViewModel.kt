package me.yeahapps.talkingphoto.feature.generating.ui.viewmodel

import android.os.CountDownTimer
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.generating.domain.repository.GeneratingRepository
import me.yeahapps.talkingphoto.feature.generating.ui.action.CreatingVideoAction
import me.yeahapps.talkingphoto.feature.generating.ui.screen.CreatingVideoScreen
import me.yeahapps.talkingphoto.feature.generating.ui.state.CreatingVideoState
import javax.inject.Inject

@HiltViewModel
class CreatingVideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val generatingRepository: GeneratingRepository,
) : BaseViewModel<CreatingVideoState, Any, CreatingVideoAction>(CreatingVideoState()) {

    private val args = savedStateHandle.toRoute<CreatingVideoScreen>()
    private var timer: CountDownTimer? = null

    override fun obtainEvent(viewEvent: Any) {}

    init {
        startCreatingVideo()
    }


    private fun startCreatingVideo() = viewModelScoped {
        fun sendError() = {
            sendAction(CreatingVideoAction.ShowVideoGeneratingError)
            return@viewModelScoped
        }

        val imageUrl = generatingRepository.uploadImage(args.imageUri.toUri())
        if (imageUrl == null) sendError()
        updateViewState { copy(isImageUploaded = true) }

        startProgressUpdateTimer()
        val audioUrl = generatingRepository.uploadAudio(args.audioUri.toUri())
        if (audioUrl == null) sendError()

        val animateImageId = generatingRepository.animateImage(imageUrl!!, audioUrl!!)
        if (animateImageId == null) sendError()

        val videoPath = generatingRepository.getVideoUrl(animateImageId!!)
        if (videoPath == null) sendError()
        timer?.cancel()
        timer = null
//        myWorksRepository.createMyWork(
//            MyWorkModel(
//                title = if (args.songName.isEmpty()) "My pet $animateImageId" else args.songName,
//                imageUrl = imageUrl,
//                videoPath = videoPath
//            )
//        )

        sendAction(CreatingVideoAction.NavigateToVideo(videoPath = videoPath!!))
        updateViewState { copy(progress = 1f) }
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