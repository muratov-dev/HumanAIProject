package me.yeahapps.talkingphoto.features.upload.ui.event

sealed interface UploadedPhotosEvent {
    data object NavigateToPhotoUpload: UploadedPhotosEvent
}