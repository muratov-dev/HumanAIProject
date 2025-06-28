package me.yeahapps.myhumanai.features.upload.ui.event

sealed interface UploadedPhotosEvent {
    data object NavigateToPhotoUpload: UploadedPhotosEvent
}