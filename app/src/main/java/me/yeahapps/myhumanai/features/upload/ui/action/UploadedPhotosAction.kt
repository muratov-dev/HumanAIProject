package me.yeahapps.myhumanai.features.upload.ui.action

sealed interface UploadedPhotosAction {

    data object NavigateToPhotoUpload: UploadedPhotosAction

}