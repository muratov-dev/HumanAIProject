package me.yeahapps.talkingphoto.feature.generating.ui.state

import android.net.Uri

data class CreatingVideoState(
    val isImageUploaded: Boolean = false,
    val progress: Float = 0f,
    val audioUri: Uri? = null,
    val imageUri: Uri? = null,
)
