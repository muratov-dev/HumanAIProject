package me.yeahapps.talkingphoto.features.generating.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme

@Composable
fun SoundControlsIconButton(
    @DrawableRes icon: Int, modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .background(color = HumanAITheme.colors.backgroundSecondary, shape = RoundedCornerShape(32.dp))
            .alpha(if (enabled) 1f else 0.5f)
            .clip(RoundedCornerShape(32.dp)), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(enabled = enabled, onClick = onClick, role = Role.Button)
        )
        Box(modifier = Modifier.size(32.dp)) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = HumanAITheme.colors.buttonTextSecondary,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}