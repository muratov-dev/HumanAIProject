package me.yeahapps.liveface.feature.upload.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import me.yeahapps.liveface.core.ui.viewmodel.BaseViewModel
import me.yeahapps.liveface.feature.upload.domain.repository.UploadRepository
import me.yeahapps.liveface.feature.upload.ui.action.UploadedPhotosAction
import me.yeahapps.liveface.feature.upload.ui.event.UploadedPhotosEvent
import me.yeahapps.liveface.feature.upload.ui.state.UploadedPhotosState
import javax.inject.Inject

@HiltViewModel
class UploadedPhotosViewModel @Inject constructor(
    private val uploadRepository: UploadRepository
) : BaseViewModel<UploadedPhotosState, UploadedPhotosEvent, UploadedPhotosAction>(UploadedPhotosState()) {

    override fun obtainEvent(viewEvent: UploadedPhotosEvent) {
        when (viewEvent) {
            UploadedPhotosEvent.NavigateToPhotoUpload -> sendAction(UploadedPhotosAction.NavigateToPhotoUpload)
            is UploadedPhotosEvent.NavigateToSoundScreen -> sendAction(UploadedPhotosAction.NavigateToSoundScreen(viewEvent.photoPath))
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