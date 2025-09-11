package me.yeahapps.liveface.feature.upload.ui.event

import android.net.Uri

sealed interface UploadPhotoEvent {

    data object NavigateUp : UploadPhotoEvent

    data class SavePhoto(val uri: Uri) : UploadPhotoEvent
}