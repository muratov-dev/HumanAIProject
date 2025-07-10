package me.yeahapps.talkingphoto.feature.upload.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.upload.domain.repository.UploadRepository
import me.yeahapps.talkingphoto.feature.upload.ui.action.UploadPhotoAction
import me.yeahapps.talkingphoto.feature.upload.ui.event.UploadPhotoEvent
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor(
    private val uploadRepository: UploadRepository
) : BaseViewModel<Any, UploadPhotoEvent, UploadPhotoAction>(Any()) {

    override fun obtainEvent(viewEvent: UploadPhotoEvent) {
        when (viewEvent) {
            UploadPhotoEvent.NavigateUp -> TODO()
            is UploadPhotoEvent.SavePhoto -> {
                viewModelScoped {
                    val imagePath = uploadRepository.saveUploadPhoto(viewEvent.uri)
                    if (imagePath != null) sendAction(UploadPhotoAction.NavigateNext(viewEvent.uri))
                }
            }
        }
    }
}