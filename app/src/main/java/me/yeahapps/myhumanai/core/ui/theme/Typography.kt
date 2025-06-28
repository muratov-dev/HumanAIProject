package me.yeahapps.myhumanai.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.yeahapps.myhumanai.R

val robotoFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_semi_bold, FontWeight.SemiBold),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_extra_bold, FontWeight.ExtraBold),
    Font(R.font.roboto_black, FontWeight.Black),
)

@Immutable
data class HumanAITypography(
    val headlineExtraBold: TextStyle = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        lineHeight = 36.sp
    ),
    val titleMedium: TextStyle = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 30.sp
    ),
    val bodySemiBold: TextStyle = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 30.sp
    ),
    val bodyRegular: TextStyle = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    val labelMedium: TextStyle = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
)

internal val LocalHumanAITypography = staticCompositionLocalOf { HumanAITypography() }