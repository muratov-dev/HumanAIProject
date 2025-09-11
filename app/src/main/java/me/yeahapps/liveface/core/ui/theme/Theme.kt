package me.yeahapps.liveface.core.ui.theme

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun HumanAITheme(content: @Composable () -> Unit) {
    val petAIColors = HumanAIColors.Dark
    val petAITypography = HumanAITypography()

    val defaultTextStyle = petAITypography.bodySemiBold

    CompositionLocalProvider(
        LocalHumanAIColors provides petAIColors,
        LocalHumanAITypography provides petAITypography,
        LocalTextStyle provides defaultTextStyle,
    ) {
        MaterialTheme(
            colorScheme = darkColorScheme(
                onSurface = HumanAITheme.colors.textPrimary,
                surface = HumanAITheme.colors.backgroundPrimary,
                background = HumanAITheme.colors.backgroundPrimary,
                primary = HumanAITheme.colors.buttonPrimaryDefault,
                onPrimary = HumanAITheme.colors.buttonTextPrimary,
            ), typography = Typography(), content = content
        )
    }
}

object HumanAITheme {
    val colors: HumanAIColors
        @Composable get() = LocalHumanAIColors.current

    val typography: HumanAITypography
        @Composable get() = LocalHumanAITypography.current
}