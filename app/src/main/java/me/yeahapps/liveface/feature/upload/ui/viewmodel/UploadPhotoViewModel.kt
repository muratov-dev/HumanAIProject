package me.yeahapps.liveface.feature.upload.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import me.yeahapps.liveface.core.ui.viewmodel.BaseViewModel
import me.yeahapps.liveface.feature.upload.domain.model.UploadType
import me.yeahapps.liveface.feature.upload.domain.repository.UploadRepository
import me.yeahapps.liveface.feature.upload.ui.action.UploadPhotoAction
import me.yeahapps.liveface.feature.upload.ui.event.UploadPhotoEvent
import me.yeahapps.liveface.feature.upload.ui.screen.UploadPhotoScreen
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val uploadRepository: UploadRepository
) : BaseViewModel<Any, UploadPhotoEvent, UploadPhotoAction>(Any()) {

    val args = savedStateHandle.toRoute<UploadPhotoScreen>()

    override fun obtainEvent(viewEvent: UploadPhotoEvent) {
        when (viewEvent) {
            UploadPhotoEvent.NavigateUp -> sendAction(UploadPhotoAction.NavigateUp)
            is UploadPhotoEvent.SavePhoto -> {
                viewModelScoped {
                    val imagePath = uploadRepository.saveUploadPhoto(
                        viewEvent.uri, shouldSaveToDb = args.uploadType == UploadType.Upload
                    )
                    if (imagePath != null) sendAction(UploadPhotoAction.NavigateNext(viewEvent.uri))
                }
            }
        }
    }
}