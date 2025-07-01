package me.yeahapps.talkingphoto.features.upload.ui.action

sealed interface UploadedPhotosAction {

    data object NavigateToPhotoUpload: UploadedPhotosAction

}