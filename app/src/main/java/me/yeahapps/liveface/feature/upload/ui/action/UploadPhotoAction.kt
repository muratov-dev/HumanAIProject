package me.yeahapps.liveface.feature.upload.ui.action

import android.net.Uri

sealed interface UploadPhotoAction {

    data object NavigateUp : UploadPhotoAction
    data class NavigateNext(val uri: Uri) : UploadPhotoAction
}