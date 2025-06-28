package me.yeahapps.myhumanai.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
data class HumanAIColors(
    val backgroundPrimary: Color = Color.Unspecified,

    val textPrimary: Color = Color.Unspecified,
    val textAccent: Color = Color.Unspecified,

    val buttonPrimaryDisabled: Color = Color.Unspecified,

    val buttonPrimaryDefault: Color = Color.Unspecified,
    val buttonSecondaryDefault: Color = Color.Unspecified,
    val buttonTertiaryDefault: Color = Color.Unspecified,

    val buttonTextPrimary: Color = Color.Unspecified,
    val buttonTextSecondary: Color = Color.Unspecified,

    val iconButtonPrimary: Color = Color.Unspecified,
    val iconButtonOnPrimary: Color = Color.Unspecified,

    val bottomNavBarContainer: Color = Color.Unspecified,
    val bottomNavBarContent: Color = Color.Unspecified,
    val bottomNavBarContentSelected: Color = Color.Unspecified,
) {

    companion object {
        @Stable
        val Dark = HumanAIColors(
            backgroundPrimary = Color(0xFF03032A),

            textPrimary = Color(0xFFFFFFFF),
            textAccent = Color(0xFFFDE16C),

            buttonPrimaryDisabled = Color(0x80FDE16C),

            buttonPrimaryDefault = Color(0xFFFDE16C),
            buttonSecondaryDefault = Color(0xFF1C1C3F),
            buttonTertiaryDefault = Color(0x1AFFFFFF),

            buttonTextPrimary = Color(0xFF000000),
            buttonTextSecondary = Color(0xFFFFFFFF),

            iconButtonPrimary = Color(0xFFFFFFFF),
            iconButtonOnPrimary = Color(0xFFFDE16C),

            bottomNavBarContainer = Color(0xFF020228),
            bottomNavBarContent = Color(0xFF7E7E7E),
            bottomNavBarContentSelected = Color(0xFFFFE26C),
        )
    }
}

internal val LocalHumanAIColors = staticCompositionLocalOf { HumanAIColors() }