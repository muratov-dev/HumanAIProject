package me.yeahapps.talkingphoto.feature.avatars.domain

import androidx.annotation.DrawableRes
import me.yeahapps.talkingphoto.R

enum class ImageStyle(@param:DrawableRes val image: Int, val styleName: String, val propmt: String) {
    Steampunk(
        R.drawable.im_style_1,
        "Steampunk",
        "A steampunk adventurer with brass goggles, a mechanical arm, and a Victorian coat standing in a retro-futuristic city with airships and steam pipes. Exaggerated facial expressions, vibrant colors, and detailed gears."
    ),
    NativeAmerican(
        R.drawable.im_style_2,
        "Native American",
        "A proud Native American chief wearing traditional feathered headdress under a golden sunset. Dramatic lighting, strong contrast, cinematic atmosphere, and cultural symbols in the background."
    ),
    Queen(
        R.drawable.im_style_3,
        "Queen",
        "A majestic queen wearing a sparkling crown and royal robe standing in a grand palace hall. Ethereal lighting, dramatic shadows, and ornate details like chandeliers and marble columns."
    ),
    KPop(
        R.drawable.im_style_4,
        "K-pop",
        "A vibrant K-pop idol striking a dynamic pose on a colorful stage with fans cheering in the background. Glowing lights, energetic motion blur, and bold fashion style."
    ),
    Poet(
        R.drawable.im_style_5,
        "Poet",
        "A contemplative poet standing alone by candlelight, surrounded by scattered papers and an open book. Soft lighting, moody atmosphere, and vintage ink textures."
    ),
    Witch(
        R.drawable.im_style_6,
        "Witch",
        "An enchanting witch casting a spell with glowing runes floating around her. Dark forest background, magical aura, and mystical lighting effects."
    ),
    Warrior(
        R.drawable.im_style_7,
        "Warrior",
        "A fierce warrior holding a flaming sword mid-battle stance. Dusty battlefield background, intense facial expression, and armor details."
    ),
    Elf(
        R.drawable.im_style_8,
        "Elf",
        "A mystical elf standing in a glowing forest clearing with fairies flying around. Magical light effects, nature-themed clothing, and serene facial expression."
    ),
    Knight(
        R.drawable.im_style_9,
        "Knight",
        "A noble knight in shining armor riding a white horse through a misty medieval landscape. Dramatic sky, castle ruins in the background, and heroic posture."
    ),
}