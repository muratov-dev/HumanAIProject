package me.yeahapps.talkingphoto.feature.upload.ui.event

sealed interface UploadedPhotosEvent {
    data object NavigateToPhotoUpload: UploadedPhotosEvent
}