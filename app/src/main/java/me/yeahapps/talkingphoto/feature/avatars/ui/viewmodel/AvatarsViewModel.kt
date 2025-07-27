package me.yeahapps.talkingphoto.feature.avatars.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.avatars.domain.repository.AvatarRepository
import me.yeahapps.talkingphoto.feature.avatars.ui.action.AvatarsAction
import me.yeahapps.talkingphoto.feature.avatars.ui.event.AvatarsEvent
import me.yeahapps.talkingphoto.feature.avatars.ui.state.AvatarsState
import javax.inject.Inject

@HiltViewModel
class AvatarsViewModel @Inject constructor(
    private val avatarRepository: AvatarRepository
) : BaseViewModel<AvatarsState, AvatarsEvent, AvatarsAction>(AvatarsState()) {

    override fun obtainEvent(viewEvent: AvatarsEvent) {
        when (viewEvent) {
            AvatarsEvent.NavigateToPhotoUpload -> sendAction(AvatarsAction.NavigateToPhotoUpload)
            is AvatarsEvent.NavigateToSoundScreen -> sendAction(AvatarsAction.NavigateToSoundScreen(viewEvent.photoPath))
        }
    }

    init {
        viewModelScoped {
            avatarRepository.getAvatars().collectLatest {
                updateViewState { copy(avatars = it) }
            }
        }
    }
}