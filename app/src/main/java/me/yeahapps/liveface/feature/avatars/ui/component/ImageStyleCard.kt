package me.yeahapps.liveface.feature.avatars.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.yeahapps.liveface.core.ui.theme.HumanAITheme
import me.yeahapps.liveface.feature.avatars.domain.ImageStyle

@Composable
fun ImageStyleCard(
    modifier: Modifier = Modifier, style: ImageStyle, isSelected: Boolean = false, onClick: (ImageStyle) -> Unit
) {
    val borderColor =
        if (isSelected) HumanAITheme.colors.buttonPrimaryDefault else HumanAITheme.colors.backgroundSecondary
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(interactionSource = null, indication = null) { onClick(style) }) {
        Image(
            painter = painterResource(style.image),
            contentDescription = style.styleName,
            modifier = Modifier
                .size(92.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(4.dp, borderColor, RoundedCornerShape(24.dp))
        )
        Text(
            text = style.styleName,
            style = HumanAITheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = HumanAITheme.colors.textPrimary,
            textAlign = TextAlign.Center
        )
    }
}