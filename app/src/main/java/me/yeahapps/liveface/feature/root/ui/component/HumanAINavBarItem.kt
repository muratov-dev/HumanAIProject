package me.yeahapps.liveface.feature.root.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import me.yeahapps.liveface.core.ui.theme.HumanAITheme

@Composable
fun HumanAINavBarItem(
    @DrawableRes icon: Int,
    @StringRes label: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    colors: HumanAINavBarColors = HumanAINavBarDefaults.colors(),
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Companion.Tab,
                interactionSource = interactionSource,
                indication = null
            ), verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor(selected)) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null
            )
            Text(
                text = stringResource(label),
                style = HumanAITheme.typography.bodySmall,
                maxLines = 1,
            )
        }
    }
}