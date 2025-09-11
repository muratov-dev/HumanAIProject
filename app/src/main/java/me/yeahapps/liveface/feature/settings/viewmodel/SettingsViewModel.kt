package me.yeahapps.liveface.feature.settings.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.yeahapps.liveface.core.data.BillingManager
import me.yeahapps.liveface.core.ui.viewmodel.BaseViewModel
import me.yeahapps.liveface.feature.settings.action.SettingsAction
import me.yeahapps.liveface.feature.settings.event.SettingsEvent
import me.yeahapps.liveface.feature.settings.state.SettingsState
import me.yeahapps.liveface.feature.videos.domain.repository.VideosRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val videosRepository: VideosRepository, private val billingManager: BillingManager
) : BaseViewModel<SettingsState, SettingsEvent, SettingsAction>(SettingsState()) {

    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.GetVideosCount -> getVideosCount()
            SettingsEvent.NavigateToMyVideos -> sendAction(SettingsAction.NavigateToMyVideos)
            SettingsEvent.NavigateToSubscriptions -> sendAction(SettingsAction.NavigateToSubscriptions)
        }
    }

    init {
        viewModelScope.launch {
            billingManager.isSubscribed.collectLatest { hasSubscription ->
                updateViewState { copy(hasSubscription = hasSubscription) }
            }
        }
    }

    private fun getVideosCount() = viewModelScoped {
        val videosCount = videosRepository.getVideosCount()
        updateViewState { copy(videosCount = videosCount) }
    }
}