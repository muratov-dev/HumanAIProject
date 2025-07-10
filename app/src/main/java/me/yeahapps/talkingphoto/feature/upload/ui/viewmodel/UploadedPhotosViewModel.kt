package me.yeahapps.talkingphoto.feature.upload.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.upload.domain.repository.UploadRepository
import me.yeahapps.talkingphoto.feature.upload.ui.action.UploadedPhotosAction
import me.yeahapps.talkingphoto.feature.upload.ui.event.UploadedPhotosEvent
import me.yeahapps.talkingphoto.feature.upload.ui.state.UploadedPhotosState
import javax.inject.Inject

@HiltViewModel
class UploadedPhotosViewModel @Inject constructor(
    private val uploadRepository: UploadRepository
) : BaseViewModel<UploadedPhotosState, UploadedPhotosEvent, UploadedPhotosAction>(UploadedPhotosState()) {

    override fun obtainEvent(viewEvent: UploadedPhotosEvent) {
        when (viewEvent) {
            UploadedPhotosEvent.NavigateToPhotoUpload -> sendAction(UploadedPhotosAction.NavigateToPhotoUpload)
        }
    }

    init {
        viewModelScoped {
            uploadRepository.saveUser()
        }
        viewModelScoped {
            uploadRepository.getUploadPhotos().collectLatest {
                updateViewState { copy(photos = it) }
            }
        }
    }
}