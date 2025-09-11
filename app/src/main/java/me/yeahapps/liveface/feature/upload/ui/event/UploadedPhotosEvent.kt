package me.yeahapps.liveface.feature.upload.ui.event

sealed interface UploadedPhotosEvent {
    data object NavigateToPhotoUpload: UploadedPhotosEvent
    data class NavigateToSoundScreen(val photoPath: String): UploadedPhotosEvent
}