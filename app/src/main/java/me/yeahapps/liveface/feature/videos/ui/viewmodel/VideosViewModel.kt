package me.yeahapps.liveface.feature.videos.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.yeahapps.liveface.core.ui.viewmodel.BaseViewModel
import me.yeahapps.liveface.feature.videos.domain.repository.VideosRepository
import me.yeahapps.liveface.feature.videos.ui.action.VideosAction
import me.yeahapps.liveface.feature.videos.ui.event.VideosEvent
import me.yeahapps.liveface.feature.videos.ui.state.VideosState
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(
    private val videosRepository: VideosRepository
) : BaseViewModel<VideosState, VideosEvent, VideosAction>(VideosState()) {

    override fun obtainEvent(viewEvent: VideosEvent) {
        when(viewEvent){
            is VideosEvent.NavigateToVideoInfo -> sendAction(VideosAction.NavigateToVideoInfo(viewEvent.videoId))
        }
    }

    init {
        viewModelScoped {
            videosRepository.getVideos().collectLatest { videos ->
                updateViewState { copy(videosList = videos) }
            }
        }
    }
}