package me.yeahapps.myhumanai.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext


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