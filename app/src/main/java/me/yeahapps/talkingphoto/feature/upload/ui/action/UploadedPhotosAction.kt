package me.yeahapps.talkingphoto.feature.upload.ui.action

sealed interface UploadedPhotosAction {

    data object NavigateToPhotoUpload: UploadedPhotosAction

}