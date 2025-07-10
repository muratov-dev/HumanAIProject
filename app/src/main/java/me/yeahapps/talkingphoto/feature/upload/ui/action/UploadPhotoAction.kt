package me.yeahapps.talkingphoto.feature.upload.ui.action

import android.net.Uri
import me.yeahapps.talkingphoto.feature.upload.ui.event.UploadPhotoEvent

sealed interface UploadPhotoAction {

    data object NavigateUp : UploadPhotoAction
    data class NavigateNext(val uri: Uri) : UploadPhotoAction
}