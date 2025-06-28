package me.yeahapps.myhumanai.core.ui.component.button.filled

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import me.yeahapps.myhumanai.core.ui.component.button.HumanAIButtonText

@Composable
fun HumanAIPrimaryButton(
    centerContent: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: HumanAIButtonColors = HumanAIButtonDefaults.colors(),
    shape: Shape = HumanAIButtonDefaults.Shape,
    minHeight: Dp = HumanAIButtonDefaults.MinHeight,
    paddingValues: PaddingValues = HumanAIButtonDefaults.DefaultPaddingValues,
) {
    Box(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .background(color = colors.containerColor(enabled), shape = shape)
            .clip(shape)
            .defaultMinSize(minHeight = minHeight)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(enabled = enabled, onClick = onClick, role = Role.Button)
        )
        CompositionLocalProvider(LocalContentColor provides colors.contentColor(enabled)) {
            HumanAIButtonText(text = centerContent)
        }
    }
}

