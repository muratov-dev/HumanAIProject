package me.yeahapps.liveface.feature.root.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import me.yeahapps.liveface.core.ui.theme.HumanAITheme

object HumanAINavBarDefaults {
    @Composable
    @Stable
    fun colors(
        containerColor: Color = HumanAITheme.colors.bottomNavBarContainer,
        contentColor: Color = HumanAITheme.colors.bottomNavBarContent,
        contentColorSelected: Color = HumanAITheme.colors.bottomNavBarContentSelected,
    ): HumanAINavBarColors = HumanAINavBarColors(
        containerColor = containerColor,
        contentColor = contentColor,
        contentColorSelected = contentColorSelected,
    )
}

data class HumanAINavBarColors(
    val containerColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val contentColorSelected: Color = Color.Unspecified,
) {

    @Stable
    fun contentColor(selected: Boolean): Color = if (selected) contentColorSelected else contentColor
}
