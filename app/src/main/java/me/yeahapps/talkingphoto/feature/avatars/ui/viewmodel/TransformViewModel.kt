package me.yeahapps.talkingphoto.feature.avatars.ui.viewmodel

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.avatars.ui.action.TransformAction
import me.yeahapps.talkingphoto.feature.avatars.ui.event.TransformEvent
import me.yeahapps.talkingphoto.feature.avatars.ui.screen.TransformScreen
import me.yeahapps.talkingphoto.feature.avatars.ui.state.TransformState
import javax.inject.Inject

@HiltViewModel
class TransformViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<TransformState, TransformEvent, TransformAction>(TransformState()) {

    val args = savedStateHandle.toRoute<TransformScreen>()

    override fun obtainEvent(viewEvent: TransformEvent) {
        when (viewEvent) {
            is TransformEvent.OnStyleSelected -> updateViewState { copy(selectedStyle = viewEvent.style) }
        }
    }

    init {
        updateViewState { copy(userImageUri = args.imageUri.toUri()) }
    }
}