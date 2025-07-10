package me.yeahapps.talkingphoto.feature.settings.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.settings.action.SettingsAction
import me.yeahapps.talkingphoto.feature.settings.event.SettingsEvent
import me.yeahapps.talkingphoto.feature.settings.state.SettingsState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

) : BaseViewModel<SettingsState, SettingsEvent, SettingsAction>(SettingsState()) {

    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.NavigateToMyVideos -> sendAction(SettingsAction.NavigateToMyVideos)
            SettingsEvent.NavigateToSubscriptions -> sendAction(SettingsAction.NavigateToSubscriptions)
        }
    }
}