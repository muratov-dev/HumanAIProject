package me.yeahapps.talkingphoto.core.ui.component.button.icons

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object HumanAIIconButtonDefaults {

    val IconSize = 24.dp
    val Shape = CircleShape

    @Composable
    @Stable
    fun colors(
        containerColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        contentColor: Color = Color.White,
        disabledContentColor: Color = Color.Unspecified,
    ): HumanAIIconButtonColors = HumanAIIconButtonColors(
        containerColor = containerColor,
        disabledContainerColor = disabledContainerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )
}

data class HumanAIIconButtonColors(
    val containerColor: Color = Color.Unspecified,
    val disabledContainerColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
) {
    @Stable
    fun containerColor(enabled: Boolean): Color = if (enabled) containerColor else disabledContainerColor

    @Stable
    fun contentColor(enabled: Boolean): Color = if (enabled) contentColor else disabledContentColor
}