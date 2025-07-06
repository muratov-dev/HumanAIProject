package me.yeahapps.talkingphoto.feature.upload.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.upload.ui.action.UploadedPhotosAction
import me.yeahapps.talkingphoto.feature.upload.ui.event.UploadedPhotosEvent
import me.yeahapps.talkingphoto.feature.upload.ui.state.UploadedPhotosState
import javax.inject.Inject

@HiltViewModel
class UploadedPhotosViewModel @Inject constructor(

) : BaseViewModel<UploadedPhotosState, UploadedPhotosEvent, UploadedPhotosAction>(UploadedPhotosState()) {

    override fun obtainEvent(viewEvent: UploadedPhotosEvent) {
        when (viewEvent) {
            UploadedPhotosEvent.NavigateToPhotoUpload -> sendAction(UploadedPhotosAction.NavigateToPhotoUpload)
        }
    }
}