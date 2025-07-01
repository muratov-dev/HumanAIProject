package me.yeahapps.talkingphoto.core.ui.component.button.filled

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme

object HumanAIButtonDefaults {

    val MinHeight = 56.dp
    val Shape = RoundedCornerShape(100.dp)

    val DefaultHorizontalPadding = 16.dp
    val DefaultVerticalPadding = 8.dp
    val DefaultPaddingValues = PaddingValues(horizontal = DefaultHorizontalPadding, vertical = DefaultVerticalPadding)

    @Composable
    fun colors(
        containerColor: Color = HumanAITheme.colors.buttonPrimaryDefault,
        disabledContainerColor: Color = HumanAITheme.colors.buttonPrimaryDisabled,
        contentColor: Color = HumanAITheme.colors.buttonTextPrimary,
        disabledContentColor: Color = HumanAITheme.colors.buttonTextPrimary,
    ) = HumanAIButtonColors(
        containerColor = containerColor,
        disabledContainerColor = disabledContainerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )
}

data class HumanAIButtonColors(
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