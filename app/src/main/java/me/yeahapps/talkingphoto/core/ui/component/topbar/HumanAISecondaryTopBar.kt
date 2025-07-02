package me.yeahapps.talkingphoto.core.ui.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.yeahapps.talkingphoto.core.ui.component.button.icons.HumanAIIconButtonDefaults
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme

@Composable
fun HumanAISecondaryTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        navigationIcon?.let { navigationIcon() } ?: Spacer(Modifier.size(HumanAIIconButtonDefaults.IconSize))
        Text(
            text = title,
            style = HumanAITheme.typography.headlineExtraBold,
            color = HumanAITheme.colors.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        actions?.let { actions() } ?: Spacer(Modifier.size(HumanAIIconButtonDefaults.IconSize))
    }
}