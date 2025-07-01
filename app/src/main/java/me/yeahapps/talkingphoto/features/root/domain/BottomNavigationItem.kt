package me.yeahapps.talkingphoto.features.root.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import me.yeahapps.talkingphoto.R

sealed class BottomNavigationItem(
    val route: String,
    @param:DrawableRes val icon: Int,
    @param:StringRes val label: Int
) {
    data object Upload : BottomNavigationItem("upload", R.drawable.ic_upload_selected, R.string.upload_label)
    data object Avatars : BottomNavigationItem("avatars", R.drawable.ic_avatars_selected, R.string.avatars_label)
    data object MyVideos : BottomNavigationItem("myVideos", R.drawable.ic_videos_selected, R.string.videos_label)
    data object Settings : BottomNavigationItem("settings", R.drawable.ic_settings_selected, R.string.settings_label)
}