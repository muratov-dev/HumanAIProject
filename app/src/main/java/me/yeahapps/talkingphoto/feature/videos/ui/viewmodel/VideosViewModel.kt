package me.yeahapps.talkingphoto.feature.videos.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.videos.domain.repository.VideosRepository
import me.yeahapps.talkingphoto.feature.videos.ui.action.VideosAction
import me.yeahapps.talkingphoto.feature.videos.ui.event.VideosEvent
import me.yeahapps.talkingphoto.feature.videos.ui.state.VideosState
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(
    private val videosRepository: VideosRepository
) : BaseViewModel<VideosState, VideosEvent, VideosAction>(VideosState()) {

    override fun obtainEvent(viewEvent: VideosEvent) {

    }

    init {
        viewModelScoped {
            videosRepository.getVideos().collectLatest { videos ->
                updateViewState { copy(videosList = videos) }
            }
        }
    }
}