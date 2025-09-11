package me.yeahapps.liveface.feature.upload.ui.action

sealed interface UploadedPhotosAction {

    data object NavigateToPhotoUpload: UploadedPhotosAction
    data class NavigateToSoundScreen(val photoPath: String): UploadedPhotosAction

}