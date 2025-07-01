package me.yeahapps.talkingphoto.core.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme

@Composable
fun Modifier.commonModifier(): Modifier {
    return fillMaxSize().background(color = HumanAITheme.colors.backgroundPrimary)
}