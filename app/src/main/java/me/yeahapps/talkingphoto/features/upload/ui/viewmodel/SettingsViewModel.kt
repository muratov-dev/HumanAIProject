package me.yeahapps.talkingphoto.features.upload.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.features.upload.ui.action.SettingsAction
import me.yeahapps.talkingphoto.features.upload.ui.event.SettingsEvent
import me.yeahapps.talkingphoto.features.upload.ui.state.SettingsState
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