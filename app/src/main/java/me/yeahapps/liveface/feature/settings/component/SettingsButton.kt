package me.yeahapps.liveface.feature.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.yeahapps.liveface.core.ui.theme.HumanAITheme

@Composable
fun SettingsButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    startContent: @Composable() (() -> Unit)? = null,
    endContent: @Composable() (() -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(14.dp),
    minHeight: Dp = 52.dp,
    paddingValues: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 16.dp)
) {
    Box(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .background(color = HumanAITheme.colors.backgroundSecondary, shape = shape)
            .clip(shape)
            .defaultMinSize(minHeight = minHeight)
            .clickable(enabled = enabled, onClick = onClick, role = Role.Button)
            .padding(paddingValues), contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.matchParentSize())
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            CompositionLocalProvider(LocalContentColor provides HumanAITheme.colors.buttonTextSecondary) {
                startContent?.let {
                    startContent()
                }
                Text(
                    text = text,
                    style = HumanAITheme.typography.bodyRegular,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                endContent?.let {
                    endContent()
                }
            }
        }
    }
}