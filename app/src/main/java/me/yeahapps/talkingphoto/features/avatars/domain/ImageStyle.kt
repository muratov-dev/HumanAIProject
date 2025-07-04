package me.yeahapps.talkingphoto.features.avatars.domain

import androidx.annotation.DrawableRes
import me.yeahapps.talkingphoto.R

enum class ImageStyle(@param:DrawableRes val image: Int, val styleName: String) {
    Steampunk(R.drawable.im_style_1, "Steampunk"),
    NativeAmerican(R.drawable.im_style_2, "Native American"),
    Queen(R.drawable.im_style_3, "Queen"),
    KPop(R.drawable.im_style_4, "K-pop"),
    Poet(R.drawable.im_style_5, "Poet"),
    Witch(R.drawable.im_style_6, "Witch"),
    Warrior(R.drawable.im_style_7, "Warrior"),
    Elf(R.drawable.im_style_8, "Elf"),
    Knight(R.drawable.im_style_9, "Knight"),
}